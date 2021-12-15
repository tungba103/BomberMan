package com.Entities.Tiles;

import com.Entities.Entity;
import com.Graphics.Screen;
import com.Graphics.Sprite;
import com.Levels.Coordinates;

public abstract class Tile extends Entity {

    public Tile(int x, int y, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

  @Override
  public boolean collide(Entity e) {
    return false;
  }

  @Override
  public void render(Screen screen) {
    screen.renderEntity( Coordinates.tileToPixel(this.x), Coordinates.tileToPixel(this.y), this);
  }

  @Override
  public void update() {}
}
