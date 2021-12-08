package com.Entities.Bomb;

import com.Entities.Entity;
import com.Entities.Mobs.Mob;
import com.Board;
import com.Graphics.Screen;

public class DirectionalExplosion extends Entity {

    protected Board board;
    protected int direction;
    private int radius;
    protected int xOrigin, yOrigin;
    protected Explosion[] explosions;

    public DirectionalExplosion(int x, int y, int direction, int radius, Board board) {
        xOrigin = x;
        yOrigin = y;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.radius = radius;
        this.board = board;

        explosions = new Explosion[ calculatePermitedDistance() ];
        createExplosions();
    }

    private void createExplosions() {
        boolean last = false;

        int x = (int)this.x;
        int y = (int)this.y;
        for (int i = 0; i < explosions.length; i++) {
            last = i == explosions.length -1 ? true : false;

            switch (direction) {
                case 0: y--; break;
                case 1: x++; break;
                case 2: y++; break;
                case 3: x--; break;
            }
            explosions[i] = new Explosion(x, y, direction, last, board);
        }
    }

    private int calculatePermitedDistance() {
        int radius = 0;
        int x = (int)this.x;
        int y = (int)this.y;
        while(radius < this.radius) {
            if(this.direction == 0) y--;
            if(this.direction == 1) x++;
            if(this.direction == 2) y++;
            if(this.direction == 3) x--;

            Entity a = board.getEntity(x, y, null);

            if(a instanceof Mob) ++radius; //explosion has to be below the mob

            if(a.collide(this) == false) //cannot pass thru
                break;

            ++radius;
        }
        return radius;
    }

    public Explosion explosionAt(int x, int y) {
        for (int i = 0; i < explosions.length; i++) {
            if(explosions[i].getX() == x && explosions[i].getY() == y)
                return explosions[i];
        }
        return null;
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

}
