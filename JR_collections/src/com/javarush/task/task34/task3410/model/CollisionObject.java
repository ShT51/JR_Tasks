package com.javarush.task.task34.task3410.model;

import java.awt.*;

public abstract class CollisionObject extends GameObject {

    public CollisionObject(int x, int y) {
        super(x, y);
    }

    public boolean isCollision(GameObject gameObject, Direction direction) {
        int sellSize = Model.FIELD_CELL_SIZE;

        switch (direction) {
            case LEFT:
                return getX() - sellSize == gameObject.getX() && gameObject.getY() == this.getY();
            case RIGHT:
                return getX() + sellSize == gameObject.getX() && gameObject.getY() == this.getY();
            case UP:
                return getY() - sellSize == gameObject.getY() && gameObject.getX() == this.getX();
            case DOWN:
                return getY() + sellSize == gameObject.getY() && gameObject.getX() == this.getX();
            default:
                return false;
        }
    }
}
