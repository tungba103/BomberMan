package com.Entities.Bomb;

import com.Entities.Entity;
import com.Entities.Mobs.Mob;
import com.Board;
import com.Game;
import com.Graphics.Screen;
import com.Graphics.Sprite;

public class Bomb extends Entity {
    protected double delayTime = 120; // = 2 seconds
    public int timeAfter = 20; //time to explosions disapear

    protected Board board;
    protected boolean allowedToPassThru = true;
    protected DirectionalExplosion[] explosions = null;
    protected boolean exploded = false;

    public Bomb(int x, int y,Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
        sprite = Sprite.bomb;
    }

    public void renderExplosions(Screen screen) {

    }

    public void explode() {
        delayTime = 0;
    }

    protected void explosion() {
        allowedToPassThru = true;
        exploded = true;

        Mob a = board.getMobAt(x, y);
        if(a != null)  {
            a.kill();
        }

        explosions = new DirectionalExplosion[4];

        for (int i = 0; i < explosions.length; i++) {
            explosions[i] = new DirectionalExplosion((int)x, (int)y, i, Game.getBombRadius(), board);
        }
    }

    public Explosion explosionAt(int x, int y) {
        if(!exploded) return null;

        for (int i = 0; i < explosions.length; i++) {
            if(explosions[i] == null) return null;
            Explosion e = explosions[i].explosionAt(x, y);
            if(e != null) return e;
        }

        return null;
    }

    public boolean isExploded() {
        return exploded;
    }


    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
