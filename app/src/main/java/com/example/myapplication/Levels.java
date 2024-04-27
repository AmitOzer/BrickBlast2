package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Levels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
    }

    public void startLevel1(View view) {
        LevelInformation levelInformation = new BasicLvl(this);
        GameView gameView = new GameView(this, levelInformation);
        setContentView(gameView);
    }
    public void startLevel2(View view) {
        LevelInformation levelInformation = new Level2(this);
        GameView gameView = new GameView(this, levelInformation);
        setContentView(gameView);
    }
    public void startLevel3(View view) {
        LevelInformation levelInformation = new Level3(this);
        GameView gameView = new GameView(this, levelInformation);
        setContentView(gameView);
    }

    public void goToMainMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}