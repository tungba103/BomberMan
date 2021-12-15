package com.Entities.Mobs.enemy;

import com.Board;
import com.Entities.Bomb.DirectionalExplosion;
import com.Entities.Entity;
import com.Entities.Message;
import com.Entities.Mobs.Mob;
import com.Entities.Mobs.Player;
import com.Entities.Mobs.enemy.AI.AI;
import com.Game;
import com.Graphics.Screen;
import com.Graphics.Sprite;
import com.Levels.Coordinates;
import java.awt.Color;

public abstract class Enemy extends Mob {

  protected int points;

  protected double speed; //Speed should change on level transition
  protected AI ai;

  //necessary to correct move
  protected final double MAX_STEPS;
  protected final double rest;
  protected double steps;

  protected int finalAnimation = 30;
  protected Sprite deadSprite;

  public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
    super(x, y, board);

    this.points = points;
    this.speed = speed;

    MAX_STEPS = Game.TILES_SIZE / this.speed;
    rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
    this.steps = MAX_STEPS;

    this.timeAfter = 20;
    this.deadSprite = dead;
  }

  /*
  |--------------------------------------------------------------------------
  | Mob Render & Update
  |--------------------------------------------------------------------------
   */
  @Override
  public void update() {
    animate();

    if(this.alive == false) {
      afterKill();
      return;
    }

    if(this.alive)
      calculateMove();
  }

  @Override
  public void render(Screen screen) {

    if(this.alive)
      chooseSprite();
    else {
      if(this.timeAfter > 0) {
        this.sprite = this.deadSprite;
        this.animate = 0;
      } else {
        this.sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, this.animate, 60);
      }

    }

    screen.renderEntity((int)this.x, (int)this.y - this.sprite.SIZE, this);
  }

  /*
  |--------------------------------------------------------------------------
  | Mob Move
  |--------------------------------------------------------------------------
   */
  @Override
  public void calculateMove() {
    int xa = 0, ya = 0;
    if(this.steps <= 0){
      this.direction = this.ai.calculateDirection();
      this.steps = MAX_STEPS;
    }

    if(this.direction == 0) ya--;
    if(this.direction == 2) ya++;
    if(this.direction == 3) xa--;
    if(this.direction == 1) xa++;

    if(canMove(xa, ya)) {
      this.steps -= 1 + rest;
      move(xa * this.speed, ya * this.speed);
      this.moving = true;
    } else {
      this.steps = 0;
      this.moving = false;
    }
  }

  @Override
  public void move(double xa, double ya) {
    if(!this.alive) return;

    this.y += ya;
    this.x += xa;
  }

  @Override
  public boolean canMove(double x, double y) {

    double xr = this.x, yr = this.y - 16; //subtract y to get more accurate results

    //the thing is, subract 15 to 16 (sprite size), so if we add 1 tile we get the next pixel tile with this
    //we avoid the shaking inside tiles with the help of steps
    if(this.direction == 0) { yr += this.sprite.getSize() -1 ; xr += this.sprite.getSize()/2; }
    if(this.direction == 1) {yr += this.sprite.getSize()/2; xr += 1;}
    if(this.direction == 2) { xr += this.sprite.getSize()/2; yr += 1;}
    if(this.direction == 3) { xr += this.sprite.getSize() -1; yr += this.sprite.getSize()/2;}

    int xx = Coordinates.pixelToTile(xr) +(int)x;
    int yy = Coordinates.pixelToTile(yr) +(int)y;

    Entity a = this.board.getEntity(xx, yy, this); //entity of the position we want to go

    return a.collide(this);
  }

  /*
  |--------------------------------------------------------------------------
  | Mob Colide & Kill
  |--------------------------------------------------------------------------
   */
  @Override
  public boolean collide(Entity e) {
    if(e instanceof DirectionalExplosion) {
      kill();
      return false;
    }

    if(e instanceof Player) {
      ((Player) e).kill();
      return false;
    }

    return true;
  }

  @Override
  public void kill() {
    if(this.alive == false) return;
    this.alive = false;

    this.board.addPoints(this.points);

    Message msg = new Message("+" + this.points, getXMessage(), getYMessage(), 2, Color.white, 14);
    this.board.addMessage(msg);
  }


  @Override
  protected void afterKill() {
    if(this.timeAfter > 0) --this.timeAfter;
    else {

      if(this.finalAnimation > 0) --this.finalAnimation;
      else
        remove();
    }
  }

  protected abstract void chooseSprite();
}
