package com.Entities.Tiles.Powerups;

import com.Entities.Tiles.Tile;
import com.Graphics.Sprite;

public abstract class Powerup extends Tile {
    
    protected int duration = -1; // -1 is infinite
    protected boolean active = false;
    protected int level;

    public Powerup(int x, int y, int level, Sprite sprite) {
        super(x, y, sprite);
        this.level = level;
    }

    public void removeLive() {
        if(duration > 0)
            duration--;

        if(duration == 0)
            active = false;
    }

    public abstract void setValues();

    public int getDuration() {
        return duration;
    }

    public int getLevel() {
        return level;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
