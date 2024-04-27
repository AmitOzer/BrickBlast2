package com.example.myapplication;


import com.example.myapplication.core.Counter;
import com.example.myapplication.core.HitListener;

/**
 * listener that helps us to control how much score the player has.
 *
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;
    private int points;
    /**
     * constructor for score tracking listener.
     *
     * @param scoreCounter - how much score the player has (Counter)
     */
    public ScoreTrackingListener(Counter scoreCounter, int points) {
        this.currentScore = scoreCounter;
        this.points = points;
    }

    @Override
    public void hitEvent(Brick beingHit, Ball hitter) {
        this.currentScore.increase(points);
    }
}
