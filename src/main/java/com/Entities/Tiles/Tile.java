package com.Entities.Tiles;

import com.Entities.Entity;
import com.Graphics.Screen;
import com.Graphics.Sprite;

public abstract class Tile extends Entity {

    public Tile(int x, int y, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

  public abstract void update();

  @Override
    public boolean collide(Entity e) {
        return false;
    }
}
