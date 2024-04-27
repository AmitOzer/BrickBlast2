package com.example.myapplication;

import com.example.myapplication.geometry.Point;

public class Velocity {
    private int x,y;

    public Velocity(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.x, p.getY() + this.y);
    }

    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        //calculation the cartesian coordinates
        int dx = (int) (speed * Math.sin(angle * Math.PI / 180));
        int dy = (int) (-speed * Math.cos(angle * Math.PI / 180));
        return new Velocity(dx, dy);
    }
}
