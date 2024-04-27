package com.example.myapplication;



import android.view.View;

import java.util.List;


public interface LevelInformation {

    int numberOfBalls();


    List<Velocity> initialBallVelocities();



    int paddleWidth();


    String levelName();


    View getBackground();




    List<Brick> bricks();


    //int numberOfBlocksToRemove();
    int pointsPerBrick();
}
