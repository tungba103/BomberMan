package com.Entities.Tiles.Powerups;

import com.Entities.Entity;
import com.Entities.Mobs.Player;
import com.Game;
import com.Graphics.Sprite;

public class PowerupSpeed extends Powerup{

    public PowerupSpeed(int x, int y, int level, Sprite sprite) {
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
        Game.addPlayerSpeed(0.1);
    }
}
