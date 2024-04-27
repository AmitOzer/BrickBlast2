package com.example.myapplication.core;
/**
 * helps us to count several object, defined by an integer.
 *
 */
public class Counter {
    private int counter;

    /**
     * constructor for counter.
     *
     * @param counter - the counter number (int)
     */
    public Counter(int counter) {
        this.counter = counter;
    }

    /**
     * accessor for counter.
     *
     * @return the counter value
     */
    public int getCounter() {
        return this.counter;
    }

    /**
     * add number to current count.
     *
     * @param number - the number
     */
    public void increase(int number) {
        this.counter += number;
    }


    /**
     * subtract number from current count.
     *
     * @param number - the number
     */
    public void decrease(int number) {
        this.counter -= number;
    }

    /**
     * get current count.
     *
     * @return the current count
     */
    public int getValue() {
        return this.counter;
    }
}
