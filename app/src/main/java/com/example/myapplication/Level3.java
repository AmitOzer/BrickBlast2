package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level3 implements LevelInformation{
    private int dWidth;
    private int dHeight;
    private List<Brick> bricks;
    private Background background;
    Context context;

    public Level3(Context context) {
        this.context = context;
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        bricks = new ArrayList<>();
        createBricks();
        this.background = new Background(context, Color.MAGENTA, levelName());

    }

    private void createBricks() {
        int rows = 5;
        int columns = 5;
        int numColors = columns;
        int[] colors = new int[numColors];
        int interval = 360/numColors;
        int currentHUE = 0;
        Random random = new Random();
        float[] hsv = {0,1,1};
        for (int i = 0; i<numColors; i++) {
            hsv[0] = currentHUE;
            colors[i] = Color.HSVToColor(hsv);
            currentHUE = random.nextInt(360);
        }
        int brickWidth = dWidth / columns;
        int brickHeight = dHeight / 24;
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                Brick brick = new Brick(context,column*brickWidth, row*brickHeight, brickWidth, brickHeight);
                hsv[0] = random.nextInt(360);
                brick.setColor(Color.HSVToColor(hsv));
                bricks.add(brick);
            }
        }
        brickWidth = dWidth / columns;
        brickHeight = dHeight / 24;
        for (int i=1; i<=columns - 2; i++)
            for (int column = i; column < columns - i; column++) {
                int row = rows + i - 1;
                Brick brick = new Brick(context,column*brickWidth, row*brickHeight, brickWidth, brickHeight);
                brick.setColor(colors[column]);
                bricks.add(brick);
            }
    }

    @Override
    public int numberOfBalls() {
        return 1;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        Velocity velocity1 = new Velocity(0,-10);
        List<Velocity> velocities = new ArrayList<>();
        velocities.add(velocity1);
        return velocities;
    }

    @Override
    public int paddleWidth() {
        return dWidth/5;
    }

    @Override
    public String levelName() {
        return "Level 3";
    }

    @Override
    public View getBackground() {
        return this.background;
    }

    @Override
    public List<Brick> bricks() {
        return this.bricks;
    }

    @Override
    public int pointsPerBrick() {
        return 10;
    }
}
