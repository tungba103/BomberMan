package com.Entities.Tiles.Powerups;

import com.Entities.Entity;
import com.Entities.Mobs.Player;
import com.Game;
import com.Graphics.Sprite;

public class PowerupFlames extends Powerup{

    public PowerupFlames(int x, int y, int level, Sprite sprite) {
        super(x, y, level, sprite);
    }

    @Override
    public boolean collide(Entity e) {

        if(e instanceof Player) {
            ((Player) e).addPowerup(this);
            remove();
            return true;
        }

        return false;
    }

    @Override
    public void setValues() {
        active = true;
        Game.addBombRadius(1);
    }
}
