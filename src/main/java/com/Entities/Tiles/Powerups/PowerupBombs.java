package com.Entities.Tiles.Powerups;

import com.Entities.Entity;
import com.Entities.Mobs.Player;
import com.Game;
import com.Graphics.Screen;
import com.Graphics.Sprite;

public class PowerupBombs extends Powerup{

    public PowerupBombs(int x, int y, int level, Sprite sprite) {
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
        Game.addBombRate(1);
    }
}
