package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.core.Collidable;
import com.example.myapplication.geometry.Point;
import com.example.myapplication.geometry.Rectangle;

public class Paddle extends View implements Collidable {
    private int left, top, width, height;
    private Paint paint;
    private Rectangle rectangle;
    private float initialX;


    public Paddle(Context context, int left, int top, int width, int height) {
        super(context);
        this.left = left;
        this.top = top;
        this.height = height;
        this.width = width;
        this.rectangle = new Rectangle(new Point(left, top), width, height);
        paint = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        paint.setColor(Color.WHITE);
        int left = (int)this.rectangle.getUpperLeft().getX();
        int top = (int)this.rectangle.getUpperLeft().getY();
        int right = (int)(this.rectangle.getUpperLeft().getX()+this.rectangle.getWidth());
        int bottom = (int)(this.rectangle.getUpperLeft().getY()+this.rectangle.getHeight());
        canvas.drawRect(left, top, right, bottom,paint);
        paint.setColor(Color.WHITE);
        canvas.drawRect(left+1, top+1, right-1, bottom-1,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                initialX = (int) x;
                break;
            case MotionEvent.ACTION_MOVE:
                if (true) {
                    int shift = (int)(x - initialX); // Adjust the divisor to control the speed
                    if (shift <= 0) {
                        moveLeft((int) -shift);
                    } else {
                        moveRight((int) shift);
                    }
                    //invalidate(); // Redraw the view
                }
                break;
        }
        initialX = (int) x;
        return true;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
            double velocitySize =
                    Math.sqrt(Math.pow(currentVelocity.getX(), 2)
                            + Math.pow(currentVelocity.getY(), 2));
            //the distance from the middle of the paddle (only in x-Axis)
            double distance =
                    collisionPoint.getX() - (this.rectangle.getUpperLeft().getX()
                            + this.rectangle.getWidth() / 2);
        /*
        if it's collide at the 3rd fifth (or at the left/right edges), it
        hits like a block
        */
            if (Math.abs(distance) <= this.rectangle.getWidth() / 10
                    || Math.abs(distance) == this.rectangle.getWidth() / 2) {
                Brick block = new Brick(getContext(), left, top, width, height);
                return block.hit(hitter, collisionPoint, currentVelocity);
            }
            //now distance is from the upper-left point
            distance += this.rectangle.getWidth() / 2;
            //now it's the x-value of the start of the fifth
            distance -= distance % (this.rectangle.getWidth() / 5);
            //the fifth where the collision occurs
            int fifth =
                    (int) ((int) (distance * 5) / this.rectangle.getWidth()) + 1;
            //the angle according to the fifth
            double angle = (fifth - 3) * 30;
            //return the new velocity
            return Velocity.fromAngleAndSpeed(angle, velocitySize);
    }

    public boolean pointInPaddle (float x, float y) {
        if (y>=top) {
            return true;
        }
        return false;
    }

    public void moveLeft(int x) {
        Velocity velocityleft = new Velocity(-x, 0);
        Point newUpperLeft =
                velocityleft.applyToPoint(this.rectangle.getUpperLeft());
        if (newUpperLeft.getX() < 0) {
            newUpperLeft = new Point(0, newUpperLeft.getY());
        }
        this.rectangle.setUpperLeft(newUpperLeft);
        //this.left = (int) newUpperLeft.getX();
    }

    public void moveRight(int x) {
        Velocity velocityRight = new Velocity(x, 0);
        Point newUpperLeft =
                velocityRight.applyToPoint(this.rectangle.getUpperLeft());
        if (newUpperLeft.getX() + this.rectangle.getWidth() > GameView.DWIDTH) {
            newUpperLeft = new Point(
                    GameView.DWIDTH - this.rectangle.getWidth(), newUpperLeft.getY());
        }
        this.rectangle.setUpperLeft(newUpperLeft);
        //this.left = (int) newUpperLeft.getX();
    }

    public void setInitialX(int initialX) {
        this.initialX = initialX;
    }

    public float getInitialX() {
        return this.initialX;
    }

    public int getPaddleWidth() {
        return this.width;
    }

    public void addLeft(int x) {
        this.left += x;
    }

    public int getPaddleY() {
        return (int)this.rectangle.getUpperLeft().getY();
    }
}
