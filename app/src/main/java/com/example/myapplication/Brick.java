package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.myapplication.core.Collidable;
import com.example.myapplication.core.HitListener;
import com.example.myapplication.core.HitNotifier;
import com.example.myapplication.geometry.Point;
import com.example.myapplication.geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Brick extends View implements Collidable , HitNotifier {
    private int left, top, width, height;
    private Paint paint;
    private Rectangle rectangle;
    private List<HitListener> hitListeners;
    private int color;
    public Brick(Context context, int left, int top, int width, int height) {
        super(context);
        //isVisible = true;
        this.left = left;
        this.top = top;
        this.height = height;
        this.width = width;
        this.rectangle = new Rectangle(new Point(left, top), width, height);
        paint = new Paint();
        hitListeners = new ArrayList<>();
        this.color = Color.BLUE;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        paint.setColor(Color.WHITE);
        canvas.drawRect(left, top, left+width, top+height,paint);
        paint.setColor(this.color);
        canvas.drawRect(left+1, top+1, left+width-1, top+height-1,paint);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.rectangle);
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        this.notifyHit(hitter);
        //if it's the right of left edges
        if (collisionPoint.getX() == this.rectangle.getUpperLeft().getX()
                || collisionPoint.getX() == this.rectangle.getUpperLeft().getX()
                + this.rectangle.getWidth()) {
            //if it's also the upper/lower edges
            if (collisionPoint.getY() == this.rectangle.getUpperLeft().getY()
                    || collisionPoint.getY()
                    == this.rectangle.getUpperLeft().getY()
                    + this.rectangle.getHeight()) {
                //change both dx and dy sign
                return new Velocity(-currentVelocity.getX(),
                        -currentVelocity.getY());
            }
            //change only dx sign
            return new Velocity(-currentVelocity.getX(),
                    currentVelocity.getY());
        }
        //if we reached here, it's only the upper/lower edges
        return new Velocity(currentVelocity.getX(), -currentVelocity.getY());
    }


    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners =
                new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        int x = 0;
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    public void removeFromGame(GameView gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void lowDown(int pixels) {
        Velocity v = new Velocity(0,pixels);
        this.top += pixels;
        this.rectangle.setUpperLeft(v.applyToPoint(this.rectangle.getUpperLeft()));
    }
}
