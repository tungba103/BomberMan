package com.Entities.Tiles.Destroyable;

import com.Entities.Bomb.DirectionalExplosion;
import com.Entities.Entity;
import com.Entities.Mobs.enemy.Kondoria;
import com.Graphics.Screen;
import com.Graphics.Sprite;
import com.Levels.Coordinates;

public class BrickTile extends DestroyableTile {

    public BrickTile(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Screen screen) {
        int x = Coordinates.tileToPixel(this.x);
        int y = Coordinates.tileToPixel(this.y);

        if(_destroyed) {
            this.sprite = movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);

            screen.renderEntityWithBelowSprite(x, y, this, _belowSprite);
        }
        else
            screen.renderEntity( x, y, this);
    }

    @Override
    public boolean collide(Entity e) {

        if(e instanceof DirectionalExplosion)
            destroy();

        if(e instanceof Kondoria)
            return true;

        return false;
    }


}

