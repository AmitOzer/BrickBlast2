package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Display;
import android.view.View;

import com.example.myapplication.core.CollisionInfo;
import com.example.myapplication.geometry.Line;
import com.example.myapplication.geometry.Point;

public class Ball extends View {
    private static final int RADIUS = 20;
    private Point center;
    private Velocity velocity = new Velocity(0, 0);
    private GameEnvironment gameEnvironment;


    public Ball(Context context, Point center) {
        super(context);

        //set display width and height
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        android.graphics.Point size = new android.graphics.Point();
        display.getSize(size);


        this.center = center;

    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //canvas.drawBitmap(ballImg, (int) this.center.getX() - width/2, (int) this.center.getY() - height/2, null);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawCircle((int)this.center.getX(), (int)this.center.getY(),RADIUS,paint);
        moveOneStep();
    }

    private Line trajectory() {
        return new Line(this.center, this.velocity.applyToPoint(this.center));
    }

    public void moveOneStep() {
        CollisionInfo collision =
                gameEnvironment.getClosestCollision(this.trajectory());
        if (collision == null) {
            this.center = this.velocity.applyToPoint(this.center);
            return;
        }
        double velocitySize =
                Math.sqrt(this.velocity.getX() * this.velocity.getX()
                        + this.velocity.getY() * this.velocity.getY());
        Velocity fix =
                new Velocity(
                        (int) (-10*this.velocity.getX() / velocitySize),
                        (int) (-10*this.velocity.getY() / velocitySize));
        this.center = fix.applyToPoint(collision.collisionPoint());
        this.velocity =
                collision.collisionObject().hit(this,
                        collision.collisionPoint(), this.velocity);
    }

    public void setGameEnvironment(GameEnvironment env) {
        this.gameEnvironment = env;
    }

    public void removeFromGame(GameView g) {
        g.removeSprite(this);
    }

}
