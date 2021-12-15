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
                if (last == false) {
                    this.sprite = Sprite.explosion_vertical2;
                } else {
                    this.sprite = Sprite.explosion_vertical_top_last2;
                }
                break;
            case 1:
                if (last == false) {
                    this.sprite = Sprite.explosion_horizontal2;
                } else {
                    this.sprite = Sprite.explosion_horizontal_right_last2;
                }
                break;
            case 2:
                if (last == false) {
                    this.sprite = Sprite.explosion_vertical2;
                } else {
                    this.sprite = Sprite.explosion_vertical_down_last2;
                }
                break;
            case 3:
                if (last == false) {
                    this.sprite = Sprite.explosion_horizontal2;
                } else {
                    this.sprite = Sprite.explosion_horizontal_left_last2;
                }
                break;
        }
    }

    @Override
    public void render(Screen screen) {
        int xt = (int) this.x << 4;
        int yt = (int) this.y << 4;

        screen.renderEntity(xt, yt, this);
    }

    @Override
    public void update() {
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
