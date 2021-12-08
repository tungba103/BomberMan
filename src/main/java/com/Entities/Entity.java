package com.Entities;

import com.Graphics.Sprite;
import com.Levels.Coordinates;

public abstract class Entity {
  protected double x, y; // tọa độ
  protected boolean removed = false; // bị phá hủy chưa
  protected Sprite sprite; // kiểu đối tượng

  public void remove() {
    removed = true;
  }

  public boolean isRemoved() {
    return removed;
  }

  public Sprite getSprite() {
    return sprite;
  }

  public abstract boolean collide(Entity e);

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public int getXTile() {
    return Coordinates.pixelToTile(x + sprite.SIZE / 2);
  }

  public int getYTile() {
    return Coordinates.pixelToTile(y - sprite.SIZE / 2);
  }

}
