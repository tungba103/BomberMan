package com.Entities.Tiles;

import com.Entities.Entity;
import com.Graphics.Sprite;

public class GrassTile extends Tile{

    public GrassTile(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
        public boolean collide(Entity e) {
        return true;
    }
}
