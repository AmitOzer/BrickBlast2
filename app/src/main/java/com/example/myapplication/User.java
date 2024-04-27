package com.example.myapplication;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {
    private String username;
    private String email;
    private String password;

    private int gamesPlayed;
    private int totalScore;
    public User() {

    }
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gamesPlayed = 0;
        this.totalScore = 0;
    }

    public String getEmail() {
        return this.email;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }

    public int getGamesPlayed() {
        return this.gamesPlayed;
    }

    public int getTotalScore() {
        return this.totalScore;
    }

    public void updateGamesPlayed() {
        this.gamesPlayed++;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

}
