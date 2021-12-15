package com.Entities.Mobs.enemy;

import com.Board;
import com.Entities.Mobs.enemy.AI.AIMedium;
import com.Game;
import com.Graphics.Sprite;

public class Kondoria extends Enemy {


  public Kondoria(int x, int y, Board board) {
    super(x, y, board, Sprite.kondoria_dead, Game.getPlayerSpeed() / 4, 1000);

    this.sprite = Sprite.kondoria_right1;

    this.ai = new AIMedium(this.board.getPlayer(), this); //TODO: implement AIHigh
    this.direction  = this.ai.calculateDirection();
  }
  /*
  |--------------------------------------------------------------------------
  | Mob Sprite
  |--------------------------------------------------------------------------
   */
  @Override
  protected void chooseSprite() {
    switch(this.direction) {
      case 0:
      case 1:
        if(this.moving)
          this.sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, this.animate, 60);
        else
          this.sprite = Sprite.kondoria_left1;
        break;
      case 2:
      case 3:
        if(this.moving)
          this.sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, this.animate, 60);
        else
          this.sprite = Sprite.kondoria_left1;
        break;
    }
  }
}
