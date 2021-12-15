package com.Entities.Mobs.enemy;

import com.Board;
import com.Entities.Mobs.enemy.AI.AIMedium;
import com.Game;
import com.Graphics.Sprite;

public class Minvo extends Enemy {


  public Minvo(int x, int y, Board board) {
    super(x, y, board, Sprite.minvo_dead, Game.getPlayerSpeed() * 2, 800);

    this.sprite = Sprite.minvo_right1;

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
          this.sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, this.animate, 60);
        else
          this.sprite = Sprite.minvo_left1;
        break;
      case 2:
      case 3:
        if(this.moving)
          this.sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, this.animate, 60);
        else
          this.sprite = Sprite.minvo_left1;
        break;
    }
  }
}
