// 216498691 Amit Ozer
package com.example.myapplication.geometry;
/**
 * Represents a Line, defined by a start point and an end point.
 *
 */
public class Line {
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

    private Point start;
    private Point end;

    // constructors

    /**
     * .
     * regular constructor that gets start and end points
     *
     * @param start - the start of the line (Point)
     * @param end   - the end of the line (Point)
     */
    public Line(Point start, Point end) {
        this.start = new Point(start);
        this.end = new Point(end);
    }

    /**
     * .
     * constructor that get the coordinates of the start and end of the line
     *
     * @param x1 - the start's x coordinate (double)
     * @param y1 - the start's y coordinate (double)
     * @param x2 - the end's x coordinate (double)
     * @param y2 - the end's y coordinate (double)
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    // Return the length of the line

    /**
     * @return the length of the line (double)
     */
    public double length() {
        return this.start.distance(this.end);
    }

    // Returns the middle point of the line

    /**
     * @return the middle point of the line (Point)
     */
    public Point middle() {
        //the x coordinate is the average of start and end x, the same for y
        double xMid = (this.start.getX() + this.end.getX()) / 2;
        double yMid = (this.start.getY() + this.end.getY()) / 2;
        return new Point(xMid, yMid);
    }

    // Returns the start point of the line

    /**
     * @return this line's start point (Point)
     */
    public Point start() {
        return this.start;
    }

    // Returns the end point of the line

    /**
     * @return this line's end point (Point)
     */
    public Point end() {
        return this.end;
    }

    //my addition function returns true if the line is above the point

    /**
     * .
     * check if a line is above a point
     *
     * @param p    - the point (Point)
     * @param line - the line (Line)
     * @return true if line is above p, false otherwise (boolean)
     */
    private boolean lineIsAbove(Point p, Line line) {
        //this is a special case where the line is parallel to the y-axis
        if (line.start.getX() == line.end.getX()) {
            //at this case we only compare the y values of p, start, end points
            return p.getY() > Math.max(line.start.getY(), line.end.getY());
        }
        //otherwise, we can present the line as y=mx+b
        double slope = (line.end.getY() - line.start.getY())
                / (line.end.getX() - line.start.getX());
        double b = line.start.getY() - slope * line.start.getX();
        //the if statement tells us if the point is above the line
        if (slope * p.getX() + b > p.getY()) {
            return true;
        }
        return false;
    }

    //my addition function returns true if the point is on the line

    /**
     * .
     * checks if the point is on the line
     *
     * @param p    - the point (Point)
     * @param line - the line (Line)
     * @return true if p is on the line, false otherwise (boolean)
     */
    private boolean onLine(Point p, Line line) {
        //this is a special case where the line is parallel to the y-axis
        if (line.start.getX() == line.end.getX()) {
            /*
            at this case x coordinate must be the same and y should be in the
            range of the line
             */
            return p.getX() == line.start.getX()
                    && ((p.getY() - line.start.getY())
                    * (p.getY() - line.end.getY()) <= 0);
        }
        //otherwise, we can present the line as y=mx+b
        double slope = (line.end.getY() - line.start.getY())
                / (line.end.getX() - line.start.getX());
        double b = line.start.getY() - slope * line.start.getX();
        //the is statement tells us if the point is on the line
        if (doubleEquals(slope * p.getX() + b, p.getY())
                && (p.getX() - line.start.getX()) * (p.getX() - line.end.getX())
                <= 0.0) {
            return true;
        }
        return false;
    }

    // Returns true if the lines intersect, false otherwise

    /**
     * .
     * check if the lines intersect
     *
     * @param other - other line we want to check with this line (Line)
     * @return true if the lines intersect, false otherwise (boolean)
     */
    public boolean isIntersecting(Line other) {
        //assign the values of the formula y=mx+b into variables
        double slope1 = 0, slope2 = 0, b1 = 0, b2 = 0;
        if (this.start.getX() != this.end.getX()) {
            slope1 =
                    (this.end.getY() - this.start.getY())
                            / (this.end.getX() - this.start.getX());
            b1 = this.start.getY() - slope1 * this.start.getX();
        }
        if (other.start.getX() != other.end.getX()) {
            slope2 =
                    (other.end.getY() - other.start.getY())
                            / (other.end.getX() - other.start.getX());
            b2 = other.start.getY() - slope2 * other.start.getX();
        }
        //if this line is parallel to y-axis
        if (this.start.getX() == this.end.getX()) {
            //if both of the lines are parallel to y-axis
            if (other.start.getX() == other.end.getX()) {
                //those line should intersect at start or end
                if (onLine(other.end, this) || onLine(other.start, this)) {
                    return true;
                }
                return false;
            }
            double x = this.start.getX();
            //p is the point on the other line with this line's x coordinate
            Point p = new Point(x, slope2 * x + b2);
            //if the lines intersects it must be on p
            if (onLine(p, this) && onLine(p, other)) {
                return true;
            }
            return false;
        }
        //if the other line is parallel to y-axis
        if (other.start.getX() == other.end.getX()) {
            //this line shouldn't be parallel to y-axis, we checked it before
            double x = other.start.getX();
            //p is the point on this line with other's x coordinate
            Point p = new Point(x, slope1 * x + b1);
            //if the lines intersects it must be on p
            if (onLine(p, this) && onLine(p, other)) {
                return true;
            }
            return false;
        }
        //if the lines are parallel to each other and not to y-axis
        if (slope1 == slope2) {
            //they must intersect at the start or at the end
            if (onLine(this.end, other) || onLine(this.start, other)) {
                return true;
            }
            return false;
        }
        //if those lines aren't parallel to each other
        //if the other line is above this whole line
        if (lineIsAbove(this.start, other)
                == lineIsAbove(this.end, other)) {
            //they can only intersect at the start or at the end
            if (onLine(this.start, other)
                    || onLine(this.end, other)) {
                return true;
            }
            return false;
        }
        //if this line is above the whole other line
        if (lineIsAbove(other.start, this)
                == lineIsAbove(other.end, this)) {
            //they can only intersect at the start or at the end
            if (onLine(other.start, this)
                    || onLine(other.end, this)) {
                return true;
            }
            return false;
        }
        //at this case they must intersect
        return true;
    }

    // Returns the intersection point if the lines intersect,
    // and null otherwise.

    /**
     * .
     * looking for the intersection point between two lines
     *
     * @param other - the other line (Line)
     * @return the intersection point if the lines intersect,and null
     * otherwise (Point)
     */
    public Point intersectionWith(Line other) {
        //if at first they're not intersects we return null
        if (!this.isIntersecting(other)) {
            return null;
        }
        //assign the values of the formula y=mx+b into variables
        double slope1 = 0, slope2 = 0, b1 = 0, b2 = 0;
        if (this.start.getX() != this.end.getX()) {
            slope1 =
                    (this.end.getY() - this.start.getY())
                            / (this.end.getX() - this.start.getX());
            b1 = this.start.getY() - slope1 * this.start.getX();
        }
        if (other.start.getX() != other.end.getX()) {
            slope2 =
                    (other.end.getY() - other.start.getY())
                            / (other.end.getX() - other.start.getX());
            b2 = other.start.getY() - slope2 * other.start.getX();
        }
        //if this line is parallel to y-axis
        if (this.start.getX() == this.end.getX()) {
            //if both of the lines are parallel to y-axis
            if (other.start.getX() == other.end.getX()) {
                //these are all the options for intersections for this case
                if (this.start.equals(other.start)) {
                    if ((this.end.getY() - this.start.getY())
                            * (other.end.getY() - other.start.getY()) < 0) {
                        return new Point(this.start);
                    }
                } else if (this.start.equals(other.end)) {
                    if ((this.start.getY() - this.end.getY())
                            * (other.end.getY() - other.start.getY()) < 0) {
                        return new Point(this.start);
                    }
                }
                if (this.end.equals(other.end)) {
                    if ((this.start.getY() - this.end.getY())
                            * (other.start.getY() - other.end.getY()) < 0) {
                        return new Point(this.end);
                    }
                } else if (this.end.equals(other.start)) {
                    if ((this.start.getY() - this.end.getY())
                            * (other.end.getY() - other.start.getY()) < 0) {
                        return new Point(this.end);
                    }
                }
                //if we reached here there's no way those lines intersect
                return null;
            }
            double x = this.start.getX();
            //p is the point on the other line with this line's x coordinate
            Point p = new Point(x, slope2 * x + b2);
            //if the lines intersects it must be on p
            if (onLine(p, this) && onLine(p, other)) {
                return p;
            }
            return null;
        }
        //if the other line is parallel to y-axis
        if (other.start.getX() == other.end.getX()) {
            double x = other.start.getX();
            //p is the point on this line with other's x coordinate
            Point p = new Point(x, slope1 * x + b1);
            //if the lines intersects it must be on p
            if (onLine(p, this) && onLine(p, other)) {
                return p;
            }
            return null;
        }

        //if the lines are parallel to each other but not to y-axis
        if (doubleEquals(slope1, slope2)) {
            //at this case those lines can't intersect
            if (b1 != b2) {
                return null;
            }
            //these are all the options for intersections for this case
            if (this.start.equals(other.start)) {
                if ((this.end.getX() - this.start.getX())
                        * (other.end.getX() - other.start.getX()) < 0) {
                    return new Point(this.start);
                }
            } else if (this.start.equals(other.end)) {
                if ((this.start.getX() - this.end.getX())
                        * (other.end.getX() - other.start.getX()) < 0) {
                    return new Point(this.start);
                }
            }
            if (this.end.equals(other.end)) {
                if ((this.start.getX() - this.end.getX())
                        * (other.start.getX() - other.end.getX()) < 0) {
                    return new Point(this.end);
                }
            } else if (this.end.equals(other.start)) {
                if ((this.start.getX() - this.end.getX())
                        * (other.end.getX() - other.start.getX()) < 0) {
                    return new Point(this.end);
                }
            }
            //if we reached here there's no way those lines intersect
            return null;
        }
        /*
        y = m1x+b1
        y = m2x+b2
        m2x+b2=m1x+b1
        x=(b2-b1)/(m1-m2)
         */
        //at this case they must intersect because we checked it before and
        // these lines aren't parallel to each other
        double xI = (b2 - b1) / (slope1 - slope2);
        double yI = slope1 * xI + b1;
        return new Point(xI, yI);
    }

    // equals -- return true is the lines are equal, false otherwise

    /**
     * .
     * checks if the lines are equal to each other
     *
     * @param other - the other line we compare to this (Line)
     * @return true if the lines are the same, false otherwise (boolean)
     */
    public boolean equals(Line other) {
        //the lines must have the same start and end (the order doesn't matter)
        if ((this.start.equals(other.start) && this.end.equals(other.end))
                || (this.start.equals(other.end)
                && this.end.equals(other.start))) {
            return true;
        }
        return false;
    }


    // If this line does not intersect with the rectangle, return null.
    // Otherwise, return the closest intersection point to the
    // start of the line.

    /**
     * returns the closest intersection point with a rectangle to the start
     * of the line.
     *
     * @param rect - the rectangle (Rectangle)
     * @return the closest point (Point)
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        //the list of the intersections
        java.util.List<Point> list = rect.intersectionPoints(this);
        if (list.isEmpty()) {
            return null;
        }
        //finding the closest point from the intersections
        Point closest = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (this.start.distance(list.get(i))
                    < this.start.distance(closest)) {
                closest = list.get(i);
            }
        }
        return closest;
    }

}