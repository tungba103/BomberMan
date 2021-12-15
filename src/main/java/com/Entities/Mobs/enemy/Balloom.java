package com.Entities.Mobs.enemy;

import com.Board;
import com.Entities.Mobs.enemy.AI.AILow;
import com.Game;
import com.Graphics.Sprite;

public class Balloom extends Enemy {


  public Balloom(int x, int y, Board board) {
    super(x, y, board, Sprite.balloom_dead, Game.getPlayerSpeed() / 2, 100);

    this.sprite = Sprite.balloom_left1;

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
        this.sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, this.animate, 60);
        break;
      case 2:
      case 3:
        this.sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, this.animate, 60);
        break;
    }
  }
}
