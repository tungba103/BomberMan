package com.Entities.Tiles;

import com.Entities.Entity;
import com.Graphics.Sprite;

public class BrickTile extends Tile{

    private final int MAXANIMATE = 7500;
    private int animate = 0;
    protected boolean destroyed = false;
    protected int timeToDisapear = 20;
    private Sprite belowSprite;

    public BrickTile(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {
        if(destroyed) {
            if(animate < MAXANIMATE)
                animate++;
            else
                animate = 0;
            if(timeToDisapear > 0)
                timeToDisapear--;
            else
                remove();
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy() {
        destroyed = true;
    }

    public void addBelowSprite(Sprite sprite) {
        belowSprite = sprite;
    }

    @Override
    public boolean collide(Entity e) {
        return true;
    }

}
