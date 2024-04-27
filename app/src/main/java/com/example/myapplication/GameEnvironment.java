package com.example.myapplication;

import com.example.myapplication.core.Collidable;
import com.example.myapplication.core.CollisionInfo;
import com.example.myapplication.geometry.Line;
import com.example.myapplication.geometry.Point;

import java.util.ArrayList;

/**
 * represents the game environment - the obstacles, defined by list of
 * collidable objects.
 *
 */
public class GameEnvironment {
    private java.util.List<Collidable> collidableList;

    /**
     * default constructor.
     */
    public GameEnvironment() {
        this.collidableList = new ArrayList<Collidable>();
    }


    /**
     * add the given collidable to the environment.
     *
     * @param c - the collidable object (Collidable)
     */
    public void addCollidable(Collidable c) {
        this.collidableList.add(c);
    }

    /**
     * accessor for the list of the collidable objects.
     *
     * @return the list of the collidable objects
     */
    public java.util.List<Collidable> getCollidables() {
        return this.collidableList;
    }

    // Assume an object moving from line.start() to line.end().
    // If this object will not collide with any of the collidables
    // in this collection, return null. Else, return the information
    // about the closest collision that is going to occur.

    /**
     * returns information about the closest collision.
     * <p>
     * if there's no obstacle on the trajectory it returns null. Otherwise,
     * it returns the information about the collision on the trajectory that
     * is the closest to the start of the trajectory
     * </p>
     *
     * @param trajectory - the trajectory (Line)
     * @return the information about the closest collision (CollisionInfo)
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Collidable closest = null;
        Point closestHit = null;
        Point hit;
        for (int i = 0; i < this.collidableList.size(); i++) {
            //hit is the point where the trajectory meets the obstacle
            hit = trajectory.closestIntersectionToStartOfLine(
                    collidableList.get(i).getCollisionRectangle());
            //if there's a collision, we check if it's the closest one
            if (hit != null) {
                if (closestHit == null || closest == null) {
                    closestHit = hit;
                    closest = collidableList.get(i);
                }

                if (hit.distance(trajectory.start())
                        < closestHit.distance(trajectory.start())) {
                    closestHit = hit;
                    closest = collidableList.get(i);
                }
            }
        }
        //if those variables are null we didn't find a collision
        if (closestHit == null || closest == null) {
            return null;
        }
        //return the information about the closest collision
        return new CollisionInfo(closestHit, closest);
    }

}