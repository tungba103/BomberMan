package com.Entities.Mobs.enemy.AI;

import java.util.Random;

public abstract class AI {

  protected Random random = new Random();

  public abstract int calculateDirection();
}
