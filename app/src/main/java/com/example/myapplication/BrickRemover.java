package com.example.myapplication;

import com.example.myapplication.core.Counter;
import com.example.myapplication.core.HitListener;

public class BrickRemover implements HitListener {
    private GameView gameView;
    private Counter remainingBlocks;

    public BrickRemover(GameView gameView, Counter remainingBlocks) {
        this.gameView = gameView;
        this.remainingBlocks = remainingBlocks;
    }

    @Override
    public void hitEvent(Brick beingHit, Ball hitter) {
        beingHit.removeFromGame(this.gameView);;
        beingHit.removeHitListener(this);
        this.remainingBlocks.decrease(1);
    }
}
