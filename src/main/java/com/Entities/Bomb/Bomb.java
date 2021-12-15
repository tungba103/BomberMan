package com.Entities.Bomb;

import com.Entities.AnimatedEntity;
import com.Entities.Entity;
import com.Entities.Mobs.Mob;
import com.Board;
import com.Entities.Mobs.Player;
import com.Game;
import com.Graphics.Screen;
import com.Graphics.Sprite;
import com.Levels.Coordinates;

public class Bomb extends AnimatedEntity {
    protected double timeToExplode = 120; // = 2 seconds
    public int timeAfter = 20; //time to explosions disapear

    protected Board board;
    protected boolean allowedToPassThru = true;
    protected DirectionalExplosion[] explosions = null;
    protected boolean exploded = false;

    public Bomb(int x, int y,Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
        this.sprite = Sprite.bomb;
    }

    public void renderExplosions(Screen screen) {
        for (int i = 0; i < this.explosions.length; i++) {
            this.explosions[i].render(screen);
        }
    }

    public void explode() {
        this.timeToExplode = 0;
    }

    protected void explosion() {
        this.allowedToPassThru = true;
        this.exploded = true;

        Mob a = this.board.getMobAt(x, y);
        if(a != null)  {
            a.kill();
        }

        this.explosions = new DirectionalExplosion[4];

        for (int i = 0; i < this.explosions.length; i++) {
            this.explosions[i] = new DirectionalExplosion((int)x, (int)y, i, Game.getBombRadius(), board);
        }
    }

    public Explosion explosionAt(int x, int y) {
        if(!this.exploded) return null;

        for (int i = 0; i < this.explosions.length; i++) {
            if(this.explosions[i] == null) return null;
            Explosion e = explosions[i].explosionAt(x, y);
            if(e != null) return e;
        }

        return null;
    }

    public boolean isExploded() {
        return this.exploded;
    }

    public void updateExplosions() {
        for (int i = 0; i < this.explosions.length; i++) {
            this.explosions[i].update();
        }
    }
    @Override
    public void update() {
        if(this.timeToExplode > 0)
            this.timeToExplode--;
        else {
            if(!this.exploded)
                explosion();
            else
                updateExplosions();

            if(this.timeAfter > 0)
                this.timeAfter--;
            else
                remove();
        }

        animate();
    }

    @Override
    public void render(Screen screen) {
        if(this.exploded) {
            this.sprite =  Sprite.bomb_exploded2;
            renderExplosions(screen);
        } else
            this.sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, this.animate, 60);

        int xt = (int)this.x << 4;
        int yt = (int)this.y << 4;

        screen.renderEntity(xt, yt , this);
    }


    @Override
    public boolean collide(Entity e) {

        if(e instanceof Player) {
            double diffX = e.getX() - Coordinates.tileToPixel(getX());
            double diffY = e.getY() - Coordinates.tileToPixel(getY());

            if(!(diffX >= -10 && diffX < 16 && diffY >= 1 && diffY <= 28)) { // differences to see if the player has moved out of the bomb, tested values
                this.allowedToPassThru = false;
            }

            return this.allowedToPassThru;
        }

        if(e instanceof DirectionalExplosion) {
            explode();
            return true;
        }

        return false;
    }
}
