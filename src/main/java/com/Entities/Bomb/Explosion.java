package com.Entities.Bomb;

import com.Entities.Entity;
import com.Board;
import com.Graphics.Screen;
import com.Graphics.Sprite;

public class Explosion extends Entity {

    protected boolean last = false;
    protected Board board;
    protected Sprite sprite1, sprite2;

    public Explosion(int x, int y, int direction, boolean last, Board board) {
        this.x = x;
        this.y = y;
        this.last = last;
        this.board = board;

        switch (direction) {
            case 0:
                if(last == false) {
                    sprite = Sprite.explosionvertical2;
                } else {
                    sprite = Sprite.explosionverticaltoplast2;
                }
                break;
            case 1:
                if(last == false) {
                    sprite = Sprite.explosionhorizontal2;
                } else {
                    sprite = Sprite.explosionhorizontalrightlast2;
                }
                break;
            case 2:
                if(last == false) {
                    sprite = Sprite.explosionvertical2;
                } else {
                    sprite = Sprite.explosionverticaldownlast2;
                }
                break;
            case 3:
                if(last == false) {
                    sprite = Sprite.explosionhorizontal2;
                } else {
                    sprite = Sprite.explosionhorizontalleftlast2;
                }
                break;
        }
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
