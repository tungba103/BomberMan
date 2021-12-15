package com.Entities.Mobs.enemy;

import com.Board;
import com.Entities.Mobs.enemy.AI.AIMedium;
import com.Game;
import com.Graphics.Sprite;

public class Oneal extends Enemy {

  public Oneal(int x, int y, Board board) {
    super(x, y, board, Sprite.oneal_dead, Game.getPlayerSpeed(), 200);

    this.sprite = Sprite.oneal_left1;

    this.ai = new AIMedium(this.board.getPlayer(), this);
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
          this.sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, this.animate, 60);
        else
          this.sprite = Sprite.oneal_left1;
        break;
      case 2:
      case 3:
        if(this.moving)
          this.sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, this.animate, 60);
        else
          this.sprite = Sprite.oneal_left1;
        break;
    }
  }
}
