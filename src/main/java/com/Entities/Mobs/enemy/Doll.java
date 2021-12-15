package com.Entities.Mobs.enemy;

import com.Board;
import com.Entities.Mobs.enemy.AI.AILow;
import com.Game;
import com.Graphics.Sprite;

public class Doll extends Enemy {


  public Doll(int x, int y, Board board) {
    super(x, y, board, Sprite.doll_dead, Game.getPlayerSpeed(), 400);

    this.sprite = Sprite.doll_right1;

    this.ai = new AILow();
    this.direction = this.ai.calculateDirection();
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
          this.sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, this.animate, 60);
        else
          this.sprite = Sprite.doll_left1;
        break;
      case 2:
      case 3:
        if(this.moving)
          this.sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, this.animate, 60);
        else
          this.sprite = Sprite.doll_left1;
        break;
    }
  }
}
