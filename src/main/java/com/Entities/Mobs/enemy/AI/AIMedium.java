package com.Entities.Mobs.enemy.AI;

import com.Entities.Mobs.Player;
import com.Entities.Mobs.enemy.Enemy;

public class AIMedium extends AI {
  Player player;
  Enemy e;

  public AIMedium(Player player, Enemy e) {
    this.player = player;
    this.e = e;
  }

  @Override
  public int calculateDirection() {

    if(this.player == null)
      return random.nextInt(4);

    int vertical = random.nextInt(2);

    if(vertical == 1) {
      int v = calculateRowDirection();
      if(v != -1)
        return v;
      else
        return calculateColDirection();

    } else {
      int h = calculateColDirection();

      if(h != -1)
        return h;
      else
        return calculateRowDirection();
    }

  }

  protected int calculateColDirection() {
    if(this.player.getXTile() < this.e.getXTile())
      return 3;
    else if(this.player.getXTile() > this.e.getXTile())
      return 1;

    return -1;
  }

  protected int calculateRowDirection() {
    if(this.player.getYTile() < this.e.getYTile())
      return 0;
    else if(this.player.getYTile() > this.e.getYTile())
      return 2;
    return -1;
  }


}
