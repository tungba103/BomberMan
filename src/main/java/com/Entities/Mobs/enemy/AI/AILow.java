package com.Entities.Mobs.enemy.AI;

public class AILow extends AI{

  @Override
  public int calculateDirection() {
    return random.nextInt(4);
  }
}
