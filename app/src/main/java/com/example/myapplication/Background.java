package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.View;

public class Background extends View {
    private int dWidth;
    private int dHeight;
    private String text;
    private int color;
    private Paint paint;
    private int xText;
    private int yText;
    public Background(Context context, int color, String text) {
        super(context);
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        this.color = color;
        this.text = text;
        this.paint = new Paint();
        xText = dWidth/2;
        yText = dHeight/2;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(this.color);
        this.paint.setColor(Color.WHITE);
        paint.setTextSize(100);
        float x = xText - (paint.measureText(text)) / 2;
        canvas.drawText(text, x, yText, paint);
    }

    public void setxText(int x) {
        this.xText = x;
    }

    public void setyText(int y) {
        this.yText = y;
    }

}
