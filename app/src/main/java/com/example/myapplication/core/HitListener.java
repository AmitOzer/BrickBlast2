package com.example.myapplication.core;

import com.example.myapplication.Ball;
import com.example.myapplication.Brick;
/**
 * part of the observer - the listener.
 *
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     * <p>
     * The hitter parameter is the Ball that's doing the hitting.
     * </p>
     *
     * @param beingHit - the block being hit (Block)
     * @param hitter   - the hitter (ball)
     */
    void hitEvent(Brick beingHit, Ball hitter);
}

