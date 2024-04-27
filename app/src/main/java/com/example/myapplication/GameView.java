package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.myapplication.core.Collidable;
import com.example.myapplication.core.Counter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends View {
    private static final long UPDATE_MILLIS = 30;
    public static int DWIDTH;
    public static int DHEIGHT;
    Context context;
    Ball ball;
    GameEnvironment gameEnv;
    Runnable runnable;
    Handler handler;
    int dWidth, dHeight;
    //Brick bricks[] = new Brick[24];
    List<View> sprites;
    BrickRemover brickRemover;
    BallRemover ballRemover;
    ScoreTrackingListener scoreTrackingListener;
    private Paddle paddle;
    private Counter brickCounter;
    private Counter ballCounter;
    private Counter scoreCounter;
    private boolean backToMain = true;
    private LevelInformation levelInformation;
    ScoreIndicator scoreIndicator;
    public GameView(Context context, LevelInformation levelInformation) {
        super(context);
        this.context = context;
        this.levelInformation = levelInformation;
        this.sprites = new ArrayList<>();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        brickCounter = new Counter(0);
        brickRemover = new BrickRemover(this, this.brickCounter);

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        DWIDTH = dWidth;
        DHEIGHT = dHeight;

        if (levelInformation.getBackground() != null) {
            sprites.add(levelInformation.getBackground());
        }

        com.example.myapplication.geometry.Point center = new com.example.myapplication.geometry.Point(size.x / 2, size.y / 2);
        gameEnv = new GameEnvironment();

        for (int i=0; i<levelInformation.numberOfBalls(); i++) {
            ball = new Ball(context,center);
            ball.setVelocity(levelInformation.initialBallVelocities().get(i));
            ball.setGameEnvironment(gameEnv);
            sprites.add(ball);
        }


        ballCounter = new Counter(levelInformation.numberOfBalls());
        ballRemover = new BallRemover(this,ballCounter);

        scoreCounter = new Counter(0);
        scoreTrackingListener = new ScoreTrackingListener(scoreCounter,levelInformation.pointsPerBrick());
        scoreIndicator = new ScoreIndicator(this.context, scoreCounter, 100, dWidth, levelInformation.levelName());

        createBricks();
        createEdges();
        sprites.add(scoreIndicator);
        int paddleWidth = levelInformation.paddleWidth();
        this.paddle = new Paddle(context, dWidth/2 - paddleWidth/2, dHeight - 400, paddleWidth, 50);
        sprites.add(paddle);
        gameEnv.addCollidable(paddle);



    }

    private void createBricks() {
        for (Brick brick: levelInformation.bricks()) {
            brick.lowDown(scoreIndicator.getIndicatorHeight());
            gameEnv.addCollidable(brick);
            sprites.add(brick);
            brick.addHitListener(brickRemover);
            brick.addHitListener(scoreTrackingListener);
            this.brickCounter.increase(1);
        }
    }

    private void createEdges() {
        Brick up = new Brick(context, -20, 0, dWidth+40, scoreIndicator.getIndicatorHeight());
        Brick left = new Brick(context, -20, -20, 20, dHeight+40);
        Brick right = new Brick(context, dWidth, -20, 20, dHeight+40);
        Brick bottom = new Brick(context, -20, dHeight, dWidth+40, 20);
        sprites.add(up);
        sprites.add(left);
        sprites.add(right);
        sprites.add(bottom);
        gameEnv.addCollidable(up);
        gameEnv.addCollidable(right);
        gameEnv.addCollidable(left);
        gameEnv.addCollidable(bottom);
        bottom.addHitListener(ballRemover);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        for (int i=0; i<sprites.size(); i++) {
            sprites.get(i).draw(canvas);
        }

        if (this.brickCounter.getValue() <= 0) {
            backToMain();
        }

        if (this.ballCounter.getValue() <= 0) {
            backToMain();
        }

        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        if (y >= paddle.getPaddleY() - dHeight/10) {
            paddle.onTouchEvent(event);
        }
        return true;
    }

    public void backToMain() {
        if (!backToMain)
            return;
        backToMain = false;

        //update stats
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userDataRef = database.getReference("Users").child(user.getUid());

        userDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userinfo = snapshot.getValue(User.class);
                if (userinfo != null) {
                    userinfo.updateGamesPlayed();
                    userinfo.setTotalScore(userinfo.getTotalScore() + GameView.this.scoreCounter.getCounter());
                    userDataRef.setValue(userinfo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Start MainActivity
        Intent intent = new Intent(getContext(), Levels.class);
        getContext().startActivity(intent);
        // Finish the current activity (GameView)
        ((Activity)getContext()).finish();
    }



    public void removeCollidable(Collidable c) {
        this.gameEnv.getCollidables().remove(c);
    }

    public void removeSprite(View s) {
        this.sprites.remove(s);
    }

    public int getdWidth() {
        return this.dWidth;
    }

    public int getdHeight() {
        return this.dHeight;
    }
}
