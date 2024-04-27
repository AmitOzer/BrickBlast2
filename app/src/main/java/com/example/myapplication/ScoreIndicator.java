package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.myapplication.core.Counter;

public class ScoreIndicator extends View {
    private static final int FONT_SIZE = 50;
    private Counter score;
    private int height;
    private int width;
    private String lvlName;
    private Paint paint;
    private int xScore;
    private int xName;
    public ScoreIndicator(Context context, Counter score, int height, int width, String lvlName) {
        super(context);
        this.score = score;
        this.height = height;
        this.width = width;
        this.lvlName = lvlName;
        this.paint = new Paint();
        this.xScore = width/3;
        this.xName = width*2/3;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        paint.setColor(Color.WHITE);
        canvas.drawRect(0,0,width,height,paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(FONT_SIZE);
        String scoreText = "Score: " + String.valueOf(this.score.getValue());
        float x = xScore - (paint.measureText(scoreText)) / 2;
        canvas.drawText(scoreText, x, height/2, paint);
        x = xName - (paint.measureText(lvlName)) / 2;
        canvas.drawText(lvlName, x, height/2, paint);
    }

    public int getIndicatorHeight() {
        return this.height;
    }
}
