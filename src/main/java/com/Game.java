package com;

import com.Exceptions.BombermanException;
import com.GUI.Frame;
import com.Graphics.Screen;
import com.input.Keyboard;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas {

  public static final double VERSION = 1.0;
  public static final int TILES_SIZE = 16;
  public static final int WIDTH = TILES_SIZE * 15;
  public static final int HEIGHT = 13 * TILES_SIZE;

  public static int SCALE = 3;

  public static final String TITLE = "Bomberman ";

  // Initial configs (mặc định)
  private static final int BOMBRATE = 1; // Số lượng bomb
  private static final int BOMBRADIUS = 1; // Tầm nổ
  private static final double PLAYERSPEED = 1.0; // Tốc chạy

  public static final int TIME = 200; // Thời gian mỗi màn
  public static final int POINTS = 0; // Điểm
  public static final int LIVES = 3; // Mạng

  protected static int SCREENDELAY = 3;

  //can be modified with bonus
  protected static int bombRate = BOMBRATE;
  protected static int bombRadius = BOMBRADIUS;
  protected static double playerSpeed = PLAYERSPEED;


  //Time in the level screen in seconds
  protected int screenDelay = SCREENDELAY;

  private Keyboard input;
  private boolean running = false;
  private boolean paused = true;

  private Board board;
  private Screen screen;
  private Frame frame;

  //this will be used to render the game, each render is a calculated image saved here
  private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
  private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

  public Game(Frame frame) throws BombermanException {
    this.frame = frame;
    this.frame.setTitle(TITLE);

    this.screen = new Screen(WIDTH, HEIGHT);
    this.input = new Keyboard();

    this.board = new Board(this, this.input, screen);
    addKeyListener(this.input);
  }


  private void renderGame() { //render will run the maximum times it can per second
    BufferStrategy bs = getBufferStrategy(); //create a buffer to store images using canvas
    // BufferStrategy use to store Graphics 2D
    if (bs == null) { //if canvas dont have a bufferstrategy, create it
      createBufferStrategy(3); //triple buffer
      return;
    }

    screen.clear();

    this.board.render(screen); // Render Entities from board to _board && to pixels in screen
    for (int i = 0; i < pixels.length; i++) { //create the image to be rendered
      pixels[i] = screen._pixels[i];
    }

    Graphics g = bs.getDrawGraphics();

    g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    this.board.renderMessages(g);

    g.dispose(); //release resources
    bs.show(); //make next buffer visible
  }

  private void renderScreen() { //TODO: merge these render methods
    BufferStrategy bs = getBufferStrategy();
    if (bs == null) {
      createBufferStrategy(3);
      return;
    }

    screen.clear();

    Graphics g = bs.getDrawGraphics();

    this.board.drawScreen(g);

    g.dispose();
    bs.show();
  }

  private void update() {
    input.update();// update key pressed and released
    board.update();// update entities to board;
  }

  public void start() {
    running = true;

    long lastTime = System.nanoTime();
    long timer = System.currentTimeMillis();
    final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second
    double delta = 0;
    int frames = 0;
    int updates = 0;
    requestFocus();
    while (running) {
      long now = System.nanoTime();
      delta += (now - lastTime) / ns;
      lastTime = now;
      while (delta >= 1) {
        update();
        updates++;
        delta--;
      }

      if (paused) {
        if (screenDelay <= 0) { //time passed? lets reset status to show the game
          board.setShow(-1);
          paused = false;
        }

        renderScreen();
      } else {
        renderGame();
      }

      frames++;
      if (System.currentTimeMillis() - timer > 1000) { //once per second
        frame.setTime(board.subtractTime());
        frame.setPoints(board.getPoints());
        frame.setLives(board.getLives());
        timer += 1000;
        frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
        updates = 0;
        frames = 0;

        if (board.getShow() == 2) {
          --screenDelay;
        }
      }
    }
  }


  /*
  |--------------------------------------------------------------------------
  | Getters & Setters
  |--------------------------------------------------------------------------
   */
  public static double getPlayerSpeed() {
    return playerSpeed;
  }

  public static int getBombRate() {
    return bombRate;
  }

  public static int getBombRadius() {
    return bombRadius;
  }

  public static void addPlayerSpeed(double i) {
    playerSpeed += i;
  }

  public static void addBombRadius(int i) {
    bombRadius += i;
  }

  public static void addBombRate(int i) {
    bombRate += i;
  }

  public int getScreenDelay() {
    return screenDelay;
  }

  public void decreaseScreenDelay() {
    screenDelay--;
  }

  public void resetScreenDelay() {
    screenDelay = SCREENDELAY;
  }

  public Keyboard getInput() {
    return input;
  }

  public Board getBoard() {
    return board;
  }

  public void run() {
    running = true;
    paused = false;
  }

  public void stop() {
    running = false;
  }

  public boolean isRunning() {
    return running;
  }

  public boolean isPaused() {
    return paused;
  }

  public void pause() {
    paused = true;
  }
}
