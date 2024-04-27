// 216498691 Amit Ozer
package com.example.myapplication.geometry;
import java.util.ArrayList;

/**
 * represent a rectangle, defined by upper left point (Point) width (int) and
 * height (int).
 *
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * regular constructor.
     *
     * @param rectangle - the rectangle (Rectangle)
     */
    public Rectangle(Rectangle rectangle) {
        this.upperLeft = new Point(rectangle.upperLeft);
        this.width = rectangle.width;
        this.height = rectangle.height;
    }


    /**
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft - the upper left point (Point)
     * @param width     - the width (int)
     * @param height    - the height (int)
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = new Point(upperLeft);
        this.width = width;
        this.height = height;
    }


    /**
     * Return a (possibly empty) List of intersection points
     * with the specified line.
     *
     * @param line - the line (Line)
     * @return the list of the intersection points (java.util.List(Point))
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        //the returned list
        java.util.List<Point> list = new ArrayList<Point>();
        //the edges of the rectangle
        Line[] edges = new Line[4];
        edges[0] = new Line(this.upperLeft.getX(), this.upperLeft.getY(),
                this.upperLeft.getX() + this.width, this.upperLeft.getY());
        edges[1] = new Line(this.upperLeft.getX(), this.upperLeft.getY(),
                this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        edges[2] = new Line(this.upperLeft.getX() + this.width,
                this.upperLeft.getY(),
                this.upperLeft.getX() + this.width,
                this.upperLeft.getY() + this.height);
        edges[3] = new Line(this.upperLeft.getX(),
                this.upperLeft.getY() + this.height,
                this.upperLeft.getX() + this.width,
                this.upperLeft.getY() + this.height);
        //p is the intersection
        Point p;
        for (Line edge : edges) {
            p = edge.intersectionWith(line);
            //add the intersection if exists
            if (p != null) {
                list.add(p);
            }
        }
        return list;
    }


    /**
     * accessor for the width.
     * @return the width (int)
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * accessor for the height.
     * @return the height (int)
     */
    public double getHeight() {
        return this.height;
    }


    /**
     * accessor for the upper left point.
     * @return the upper left point (Point)
     */
    public Point getUpperLeft() {
        return new Point(this.upperLeft);
    }

    /**
     * set the upper left point.
     * @param upperLeft - the point (Point)
     */
    public void setUpperLeft(Point upperLeft) {
        this.upperLeft = new Point(upperLeft);
    }
}