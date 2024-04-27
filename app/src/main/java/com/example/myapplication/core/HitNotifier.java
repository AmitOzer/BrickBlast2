package com.example.myapplication.core;

/**
 * part of the observer pattern - the notifier.
 *
 */
public interface HitNotifier {

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl - the hl (HitListener)
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl - the hl (HitListener)
     */
    void removeHitListener(HitListener hl);
}