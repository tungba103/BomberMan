package com.Entities.Mobs;

import com.Entities.Bomb.Bomb;
import com.Entities.Bomb.DirectionalExplosion;
import com.Entities.Entity;
import com.Entities.Message;
import com.Entities.Tiles.Powerups.Powerup;
import com.Board;
import com.Game;
import com.Graphics.Sprite;
import com.input.Keyboard;
import com.Levels.Coordinates;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player extends Mob{

    private List<Bomb> bombs;
    protected Keyboard input;

    protected int timeBetweenPutBombs = 0;

    public static List<Powerup> powerups = new ArrayList<Powerup>();


    public Player(int x, int y, Board board) {
        super(x, y, board);
        bombs = board.getBombs();
        input = board.getInput();
        sprite = Sprite.playerright;
    }


    /*
    |--------------------------------------------------------------------------
    | Mob Unique
    |--------------------------------------------------------------------------
     */
    private void detectPlaceBomb() {
        if(input.space && Game.getBombRate() > 0 && timeBetweenPutBombs < 0) {

            int xt = Coordinates.pixelToTile(x + sprite.getSize() / 2);
            int yt = Coordinates.pixelToTile( (y + sprite.getSize() / 2) - sprite.getSize() ); //subtract half player height and minus 1 y position

            placeBomb(xt,yt);
            Game.addBombRate(-1);

            timeBetweenPutBombs = 30;
        }
    }

    protected void placeBomb(int x, int y) {
        Bomb b = new Bomb(x, y, board);
        board.addBomb(b);
    }

    private void clearBombs() {
        Iterator<Bomb> bs = bombs.iterator();

        Bomb b;
        while(bs.hasNext()) {
            b = bs.next();
            if(b.isRemoved())  {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    /*
    |--------------------------------------------------------------------------
    | Mob Colide & Kill
    |--------------------------------------------------------------------------
     */
    @Override
    public void kill() {
        if(!alive) return;

        alive = false;

        board.addLives(-1);

        Message msg = new Message("-1 LIVE", getXMessage(), getYMessage(), 2, 1, 14);
        board.addMessage(msg);
    }

    @Override
    protected void afterKill() {
        if(timeAfter > 0) --timeAfter;
        else {
            if(bombs.size() == 0) {

                if(board.getLives() == 0)
                    board.endGame();
                else
                    board.restartLevel();
            }
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Mob Movement
    |--------------------------------------------------------------------------
     */
    @Override
    protected void calculateMove() {
        int xa = 0, ya = 0;
        if(input.up) ya--;
        if(input.down) ya++;
        if(input.left) xa--;
        if(input.right) xa++;

        if(xa != 0 || ya != 0)  {
            move(xa * Game.getPlayerSpeed(), ya * Game.getPlayerSpeed());
            moving = true;
        } else {
            moving = false;
        }

    }

    @Override
    public boolean canMove(double x, double y) {
        for (int c = 0; c < 4; c++) { //colision detection for each corner of the player
            double xt = ((x + x) + c % 2 * 11) / Game.TILESSIZE; //divide with tiles size to pass to tile coordinate
            double yt = ((y + y) + c / 2 * 12 - 13) / Game.TILESSIZE; //these values are the best from multiple tests

            Entity a = board.getEntity(xt, yt, this);

            if(!a.collide(this))
                return false;
        }

        return true;
    }

    @Override
    public void move(double xa, double ya) {
        if(xa > 0) direction = 1;
        if(xa < 0) direction = 3;
        if(ya > 0) direction = 2;
        if(ya < 0) direction = 0;

        if(canMove(0, ya)) { //separate the moves for the player can slide when is colliding
            y += ya;
        }

        if(canMove(xa, 0)) {
            x += xa;
        }
    }

    @Override
    public boolean collide(Entity e) {
        if(e instanceof DirectionalExplosion) {
            kill();
            return false;
        }

        if(e instanceof Enemy) {
            kill();
            return true;
        }

        return true;
    }

    /*
    |--------------------------------------------------------------------------
    | Powerups
    |--------------------------------------------------------------------------
     */
    public void addPowerup(Powerup p) {
        if(p.isRemoved()) return;

        powerups.add(p);

        p.setValues();
    }

    public void clearUsedPowerups() {
        Powerup p;
        for (int i = 0; i < powerups.size(); i++) {
            p = powerups.get(i);
            if(p.isActive() == false)
                powerups.remove(i);
        }
    }

    public void removePowerups() {
        for (int i = 0; i < powerups.size(); i++) {
            powerups.remove(i);
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Mob Sprite
    |--------------------------------------------------------------------------
     */
    /*
    private void chooseSprite() {
        switch(direction) {
            case 0:
                sprite = Sprite.playerup;
                if(moving) {
                    sprite = Sprite.movingSprite(Sprite.playerup1, Sprite.playerup2, animate, 20);
                }
                break;
            case 1:
                sprite = Sprite.playerright;
                if(moving) {
                    sprite = Sprite.movingSprite(Sprite.playerright1, Sprite.playerright2, animate, 20);
                }
                break;
            case 2:
                sprite = Sprite.playerdown;
                if(moving) {
                    sprite = Sprite.movingSprite(Sprite.playerdown1, Sprite.playerdown2, animate, 20);
                }
                break;
            case 3:
                sprite = Sprite.playerleft;
                if(moving) {
                    sprite = Sprite.movingSprite(Sprite.playerleft1, Sprite.playerleft2, animate, 20);
                }
                break;
            default:
                sprite = Sprite.playerright;
                if(moving) {
                    sprite = Sprite.movingSprite(Sprite.playerright1, Sprite.playerright2, animate, 20);
                }
                break;
        }
    }
     */
}
