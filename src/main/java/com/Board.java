package com;

import com.Entities.Bomb.Bomb;
import com.Entities.Bomb.Explosion;
import com.Entities.Entity;
import com.Entities.Message;
import com.Entities.Mobs.Mob;
import com.Entities.Mobs.Player;
import com.Entities.Tiles.Powerups.Powerup;
import com.Graphics.IRender;
import com.Graphics.Screen;
import com.Levels.FileLevel;
import com.Levels.Level;
import com.Exceptions.LoadLevelException;

import com.input.Keyboard;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import java.awt.Font;
import java.awt.Graphics;

public class Board implements IRender {

  public int width, height;
  protected Level level;
  protected Game game;
  protected Keyboard input;
  protected Screen screen;

  public Entity[] entities;
  public List<Mob> mobs = new ArrayList<Mob>();
  protected List<Bomb> bombs = new ArrayList<Bomb>();
  private List<Message> messages = new ArrayList<Message>();

  private int screenToShow = -1; //1:endgame, 2:changelevel, 3:paused

  private int time = Game.TIME;
  private int points = Game.POINTS;
  private int lives = Game.LIVES;

  public Board(Game game, Keyboard input, Screen screen) {
    this.game = game;
    this.input = input;
    this.screen = screen;

    changeLevel(1); //start in level 1
  }

  /*
  |--------------------------------------------------------------------------
  | Render & Update
  |--------------------------------------------------------------------------
   */
  @Override
  public void update() {
    if (game.isPaused()) {
      return;
    }

    updateEntities(); // update int[]pixels các Entities vào biến Entities của Board
    updateMobs(); // update Mobs vào biến List<Mobs> của Board
    updateBombs();// update Bombs vào biến List<Bombs> của Board
    updateMessages(); // ...
    detectEndGame();// ...

    for (int i = 0; i < mobs.size(); i++) {
      Mob a = mobs.get(i);
      if (((Entity) a).isRemoved()) {
        mobs.remove(i);
      }
    }
  }

  @Override
  public void render(Screen screen) {
    if (game.isPaused()) {
      return;
    }

    //only render the visible part of screen
    int x0 = Screen.xOffset >> 4; //tile precision, -> left X
    int x1 = (Screen.xOffset + screen.getWidth() + Game.TILES_SIZE) / Game.TILES_SIZE; // -> right X
    int y0 = Screen.yOffset >> 4;
    int y1 = (Screen.yOffset + screen.getHeight())
        / Game.TILES_SIZE; //render one tile plus to fix black margins

    for (int y = y0; y < y1; y++) {
      for (int x = x0; x < x1; x++) {
        entities[x + y * level.getWidth()].render(screen); // Render các Entities vào biến Entities[] của Board
      }
    }
    renderBombs(screen); // render vào List<Bombs>
    renderMobs(screen); // render vào List<Mobs>

  }

  /*
  |--------------------------------------------------------------------------
  | ChangeLevel
  |--------------------------------------------------------------------------
   */
  public void newGame() {
    resetProperties();
    changeLevel(1);
  }

  @SuppressWarnings("static-access")
  private void resetProperties() {
    points = Game.POINTS;
    lives = Game.LIVES;
    Player.powerups.clear();

    game.playerSpeed = 1.0;
    game.bombRadius = 1;
    game.bombRate = 1;

  }

  public void restartLevel() {
    changeLevel(level.getLevel());
  }

  public void nextLevel() {
    changeLevel(level.getLevel() + 1);
  }

  public void changeLevel(int level) {
    time = Game.TIME;
    screenToShow = 2;
    game.resetScreenDelay();
    game.pause();
    mobs.clear();
    bombs.clear();
    messages.clear();

    try {
      this.level = new FileLevel("Level" + level + ".txt", this);
      this.entities = new Entity[this.level.getHeight() * this.level.getWidth()];
      System.out.println(this.level);
      this.level.createEntities();
    } catch (LoadLevelException e) {
      endGame(); //failed to load.. so.. no more levels?
    }
  }

  public void changeLevelByCode(String str) {
    int i = this.level.validCode(str);

    if (i != -1) {
      changeLevel(i + 1);
    }
  }

  public boolean isPowerupUsed(int x, int y, int level) {
    Powerup p;
    for (int i = 0; i < Player.powerups.size(); i++) {
      p = Player.powerups.get(i);
      if (p.getX() == x && p.getY() == y && level == p.getLevel()) {
        return true;
      }
    }

    return false;
  }

  /*
  |--------------------------------------------------------------------------
  | Detections
  |--------------------------------------------------------------------------
   */
  protected void detectEndGame() {
    if (this.time <= 0) {
      restartLevel();
    }
  }

  public void endGame() {
    screenToShow = 1;
    game.resetScreenDelay();
    game.pause();
  }

  public boolean detectNoEnemies() {
    int total = 0;
    for (int i = 0; i < mobs.size(); i++) {
      if (mobs.get(i) instanceof Player == false) {
        ++total;
      }
    }

    return total == 0;
  }

  /*
  |--------------------------------------------------------------------------
  | Pause & Resume
  |--------------------------------------------------------------------------
   */
  public void gamePause() {
    game.resetScreenDelay();
    if (screenToShow <= 0) {
      screenToShow = 3;
    }
    game.pause();
  }

  public void gameResume() {
    game.resetScreenDelay();
    screenToShow = -1;
    game.run();
  }

  /*
  |--------------------------------------------------------------------------
  | Screens
  |--------------------------------------------------------------------------
   */
  public void drawScreen(Graphics g) {
    switch (screenToShow) {
      case 1:
        screen.drawEndGame(g, points, level.getActualCode());
        break;
      case 2:
        screen.drawChangeLevel(g, level.getLevel());
        break;
      case 3:
        screen.drawPaused(g);
        break;
    }
  }

  /*
  |--------------------------------------------------------------------------
  | Getters And Setters
  |--------------------------------------------------------------------------
   */
  public Entity getEntity(double x, double y, Mob m) {

    Entity res = null;

    res = getExplosionAt((int) x, (int) y);
    if (res != null) {
      return res;
    }

    res = getBombAt(x, y);
    if (res != null) {
      return res;
    }

    res = getMobAtExcluding((int) x, (int) y, m);
    if (res != null) {
      return res;
    }

    res = getEntityAt((int) x, (int) y);

    return res;
  }

  public List<Bomb> getBombs() {
    return bombs;
  }

  public Bomb getBombAt(double x, double y) {
    Iterator<Bomb> bs = bombs.iterator();
    Bomb b;
    while (bs.hasNext()) {
      b = bs.next();
      if (b.getX() == (int) x && b.getY() == (int) y) {
        return b;
      }
    }

    return null;
  }

  public Mob getMobAt(double x, double y) {
    Iterator<Mob> itr = mobs.iterator();

    Mob cur;
    while (itr.hasNext()) {
      cur = itr.next();

      if (cur.getXTile() == x && cur.getYTile() == y) {
        return cur;
      }
    }

    return null;
  }

  public Player getPlayer() {
    Iterator<Mob> itr = mobs.iterator();

    Mob cur;
    while (itr.hasNext()) {
      cur = itr.next();

      if (cur instanceof Player) {
        return (Player) cur;
      }
    }

    return null;
  }

  public Mob getMobAtExcluding(int x, int y, Mob a) {
    Iterator<Mob> itr = mobs.iterator();

    Mob cur;
    while (itr.hasNext()) {
      cur = itr.next();
      if (cur == a) {
        continue;
      }

      if (cur.getXTile() == x && cur.getYTile() == y) {
        return cur;
      }

    }

    return null;
  }

  public Explosion getExplosionAt(int x, int y) {
    Iterator<Bomb> bs = bombs.iterator();
    Bomb b;
    while (bs.hasNext()) {
      b = bs.next();

      Explosion e = b.explosionAt(x, y);
      if (e != null) {
        return e;
      }

    }

    return null;
  }

  public Entity getEntityAt(double x, double y) {
    return entities[(int) x + (int) y * level.getWidth()];
  }

  /*
  |--------------------------------------------------------------------------
  | Adds and Removes
  |--------------------------------------------------------------------------
   */
  public void addEntitie(int pos, Entity e) {
    entities[pos] = e;
  }

  public void addMob(Mob e) {
    mobs.add(e);
  }

  public void addBomb(Bomb e) {
    bombs.add(e);
  }

  public void addMessage(Message e) {
    this.messages.add(e);
  }

  /*
  |--------------------------------------------------------------------------
  | Renders
  |--------------------------------------------------------------------------
   */
  protected void renderEntities(Screen screen) {
    for (int i = 0; i < this.entities.length; i++) {
      this.entities[i].render(screen);
    }
  }

  protected void renderMobs(Screen screen) {
    Iterator<Mob> itr = mobs.iterator();

    while (itr.hasNext()) {
      itr.next().render(screen);
    }
  }

  protected void renderBombs(Screen screen) {
    Iterator<Bomb> itr = bombs.iterator();

    while (itr.hasNext()) {
      itr.next().render(screen);
    }
  }

  public void renderMessages(Graphics g) {
    Message m;
    for (int i = 0; i < this.messages.size(); i++) {
      m = this.messages.get(i);

      g.setFont(new Font("Arial", Font.PLAIN, m.getSize()));
      g.setColor(m.getColor());
      g.drawString(m.getMessage(), (int) m.getX() - Screen.xOffset * Game.SCALE, (int) m.getY());
    }
  }

  /*
  |--------------------------------------------------------------------------
  | Updates
  |--------------------------------------------------------------------------
   */
  protected void updateEntities() {
    if (this.game.isPaused()) {
      return;
    }
    for (int i = 0; i < this.entities.length; i++) {
      this.entities[i].update();
    }
  }

  protected void updateMobs() {
    if (this.game.isPaused()) {
      return;
    }
    Iterator<Mob> itr = this.mobs.iterator();

    while (itr.hasNext() && !this.game.isPaused()) {
      itr.next().update();
    }
  }

  protected void updateBombs() {
    if (this.game.isPaused()) {
      return;
    }
    Iterator<Bomb> itr = this.bombs.iterator();

    while (itr.hasNext()) {
      itr.next().update();
    }
  }

  protected void updateMessages() {
    if (this.game.isPaused()) {
      return;
    }
    Message m;
    int left = 0;
    for (int i = 0; i < this.messages.size(); i++) {
      m = this.messages.get(i);
      left = m.getDuration();

      if (left > 0) {
        m.setDuration(--left);
      } else {
        this.messages.remove(i);
      }
    }
  }

  /*
  |--------------------------------------------------------------------------
  | Getters & Setters
  |--------------------------------------------------------------------------
   */
  public Keyboard getInput() {
    return input;
  }

  public Level getLevel() {
    return level;
  }

  public Game getGame() {
    return game;
  }

  public int getShow() {
    return screenToShow;
  }

  public void setShow(int i) {
    screenToShow = i;
  }

  public int getTime() {
    return time;
  }

  public int getLives() {
    return lives;
  }

  public int subtractTime() {
    if (game.isPaused()) {
      return this.time;
    } else {
      return this.time--;
    }
  }

  public int getPoints() {
    return points;
  }

  public void addPoints(int points) {
    this.points += points;
  }

  public void addLives(int lives) {
    this.lives += lives;
  }

  public int getWidth() {
    return level.getWidth();
  }

  public int getHeight() {
    return level.getHeight();
  }

}
