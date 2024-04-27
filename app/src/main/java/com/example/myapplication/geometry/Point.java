// 216498691 Amit Ozer
package com.example.myapplication.geometry;
/**
 * .
 * Represents a Point, defined by x,y coordinate.
 *
 */
public class Point {

    private static final double COMPARISON_THRESHOLD = 0.00001;

    /**
     * .
     * helps us to compare doubles (sometimes there's a small error during
     * calculations)
     *
     * @param a - first double variable (double)
     * @param b - second double variable (double)
     * @return true if a and b are almost equals, false otherwise (boolean)
     */
    public static boolean doubleEquals(double a, double b) {
        //the numbers have to be very close
        return Math.abs(a - b) < COMPARISON_THRESHOLD;
    }

    private double x;
    private double y;

    // constructors

    /**
     * .
     * constructor for a point by x,y
     *
     * @param x - x coordinate (double)
     * @param y - y coordinate (double)
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //My addition constructor

    /**
     * .
     * constructor for a point by giving another point
     *
     * @param other - the point we want to copy (Point)
     */
    public Point(Point other) {
        this.x = other.x;
        this.y = other.y;
    }


    /**
     * .
     * return the distance of this point to the other point
     *
     * @param other - the point we want to calculate the distance to (Point)
     * @return the distance between this point and other point (double)
     */
    public double distance(Point other) {
        //d = sqrt((x1-x2)^2 + (y1-y2)^2)
        return Math.sqrt(
                Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }


    /**
     * .
     * return true is the points are equal, false otherwise
     *
     * @param other - a point we want to compare (Point)
     * @return true if this point has the same coordinates as other (boolean)
     */
    public boolean equals(Point other) {
        //both coordinates must be equals
        return doubleEquals(this.x, other.getX()) && doubleEquals(this.y,
                other.getY());
    }


    /**
     * .
     * Return the x value of this point
     *
     * @return this x coordinate (double)
     */
    public double getX() {
        return this.x;
    }

    /**
     * .
     * Return the y value of this point
     *
     * @return this y coordinate (double)
     */
    public double getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return ("X: " + this.x + " Y: " + this.y);
    }
}