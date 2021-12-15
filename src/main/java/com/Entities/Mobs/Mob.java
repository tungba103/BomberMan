package com.Entities.Mobs;

import com.Entities.AnimatedEntity;
import com.Board;
import com.Entities.Entity;
import com.Game;

public abstract class Mob extends AnimatedEntity {

    protected Board board;
    protected int direction = -1;
    protected boolean alive = true;
    protected boolean moving = false;
    public int timeAfter = 80;

    public Mob(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
    }

    protected abstract void calculateMove();

    protected abstract void move(double xa, double ya);

    public abstract void kill();

    protected abstract void afterKill();

    protected abstract boolean canMove(double x, double y);

    public boolean isAlive() {
        return alive;
    }

    public boolean isMoving() {
        return moving;
    }

    public int getDirection() {
        return direction;
    }

    protected double getXMessage() {
        return (this.x * Game.SCALE) + (this.sprite.SIZE / 2 * Game.SCALE);
    }

    protected double getYMessage() {
        return (this.y * Game.SCALE) - (this.sprite.SIZE / 2 * Game.SCALE);
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
