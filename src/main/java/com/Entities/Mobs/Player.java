package com.Entities.Mobs;

import com.Board;
import com.Entities.Bomb.Bomb;
import com.Entities.Bomb.DirectionalExplosion;
import com.Entities.Entity;
import com.Entities.Message;
import com.Entities.Mobs.enemy.Enemy;
import com.Entities.Tiles.Powerups.Powerup;
import com.Game;
import com.Graphics.Screen;
import com.Graphics.Sprite;
import com.Levels.Coordinates;
import com.input.Keyboard;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player extends Mob {

    private List<Bomb> bombs;
    protected Keyboard input;

    protected int timeBetweenPutBombs = 0;

    public static List<Powerup> powerups = new ArrayList<Powerup>();


    public Player(int x, int y, Board board) {
        super(x, y, board);
        this.bombs = board.getBombs();
        this.input = board.getInput();
        this.sprite = Sprite.player_right;
    }


    /*
    |--------------------------------------------------------------------------
    | Update & Render
    |--------------------------------------------------------------------------
     */
    @Override
    public void update() {
        clearBombs();
        if(this.alive == false) {
            afterKill();
            return;
        }

        if(this.timeBetweenPutBombs < -7500) this.timeBetweenPutBombs = 0; else this.timeBetweenPutBombs--; //dont let this get tooo big

        animate();

        calculateMove();

        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if(this.alive)
            chooseSprite();
        else
            this.sprite = Sprite.player_dead1;

        screen.renderEntity((int)this.x, (int)this.y - this.sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(this.board, this);
        Screen.setOffset(xScroll, 0);
    }


    /*
    |--------------------------------------------------------------------------
    | Mob Unique
    |--------------------------------------------------------------------------
     */
    private void detectPlaceBomb() {
        if(this.input.space && Game.getBombRate() > 0 && this.timeBetweenPutBombs < 0) {

            int xt = Coordinates.pixelToTile(this.x + this.sprite.getSize() / 2);
            int yt = Coordinates.pixelToTile( (this.y + this.sprite.getSize() / 2) - this.sprite.getSize() ); //subtract half player height and minus 1 y position

            placeBomb(xt,yt);
            Game.addBombRate(-1);

            this.timeBetweenPutBombs = 30;
        }
    }

    protected void placeBomb(int x, int y) {
        Bomb b = new Bomb(x, y, this.board);
        this.board.addBomb(b);
    }

    private void clearBombs() {
        Iterator<Bomb> bs = this.bombs.iterator();

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
        if(!this.alive) return;

        this.alive = false;

        this.board.addLives(-1);

        Message msg = new Message("-1 LIVE", getXMessage(), getYMessage(), 2, Color.white, 14);
        this.board.addMessage(msg);
    }

    @Override
    protected void afterKill() {
        if(this.timeAfter > 0) --this.timeAfter;
        else {
            if(this.bombs.size() == 0) {

                if(this.board.getLives() == 0)
                    this.board.endGame();
                else
                    this.board.restartLevel();
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
        if(this.input.up) ya--;
        if(this.input.down) ya++;
        if(this.input.left) xa--;
        if(this.input.right) xa++;

        if(xa != 0 || ya != 0)  {
            move(xa * Game.getPlayerSpeed(), ya * Game.getPlayerSpeed());
            this.moving = true;
        } else {
            this.moving = false;
        }

    }

    @Override
    public boolean canMove(double x, double y) {
        for (int c = 0; c < 4; c++) { //colision detection for each corner of the player
            double xt = ((this.x + x) + c % 2 * 11) / Game.TILES_SIZE; //divide with tiles size to pass to tile coordinate
            double yt = ((this.y + y) + c / 2 * 12 - 13) / Game.TILES_SIZE; //these values are the best from multiple tests

            Entity a = this.board.getEntity(xt, yt, this);

            if(!a.collide(this))
                return false;
        }

        return true;
    }

    @Override
    public void move(double xa, double ya) {
        if(xa > 0) this.direction = 1;
        if(xa < 0) this.direction = 3;
        if(ya > 0) this.direction = 2;
        if(ya < 0) this.direction = 0;

        if(canMove(0, ya)) { //separate the moves for the player can slide when is colliding
            this.y += ya;
        }

        if(canMove(xa, 0)) {
            this.x += xa;
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

        this.powerups.add(p);

        p.setValues();
    }

    public void clearUsedPowerups() {
        Powerup p;
        for (int i = 0; i < this.powerups.size(); i++) {
            p = this.powerups.get(i);
            if(p.isActive() == false)
                this.powerups.remove(i);
        }
    }

    public void removePowerups() {
        for (int i = 0; i < this.powerups.size(); i++) {
            this.powerups.remove(i);
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Mob Sprite
    |--------------------------------------------------------------------------
     */
    private void chooseSprite() {
        switch(this.direction) {
            case 0:
                this.sprite = Sprite.player_up;
                if(this.moving) {
                    this.sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, this.animate, 20);
                }
                break;
            case 1:
                this.sprite = Sprite.player_right;
                if(this.moving) {
                    this.sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, this.animate, 20);
                }
                break;
            case 2:
                this.sprite = Sprite.player_down;
                if(this.moving) {
                    this.sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, this.animate, 20);
                }
                break;
            case 3:
                this.sprite = Sprite.player_left;
                if(this.moving) {
                    this.sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, this.animate, 20);
                }
                break;
            default:
                this.sprite = Sprite.player_right;
                if(this.moving) {
                    this.sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, this.animate, 20);
                }
                break;
        }
    }
}

