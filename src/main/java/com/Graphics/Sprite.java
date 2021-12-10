package com.Graphics;

public class Sprite {

  public static Sprite grass;
  public static Sprite playerright;
  public static Sprite playerdead1;
  public static Sprite explosionvertical2;
  public static Sprite explosionverticaltoplast2;
  public static Sprite explosionhorizontal2;
  public static Sprite explosionhorizontalrightlast2;
  public static Sprite explosionverticaldownlast2;
  public static Sprite explosionhorizontalleftlast2;
  public static Sprite bomb;
  public static Sprite bombexploded2;
  public static Object bomb1;
  public static Object bomb2;
  public static Sprite wall;
  public static Sprite brick;
  public static Sprite powerupbombs;
  public static Sprite portal;
  public static Sprite powerupflames;
  public static Sprite powerupspeed;
  public final int SIZE;
  private int x, y;
  public int[] pixels;
  protected int realWidth;
  protected int readHeight;
  private SpriteSheet sheet;

  public Sprite(int SIZE, int color) {
    this.SIZE = SIZE;
    pixels = new int[SIZE * SIZE];
    setColor(color);
  }

  public Sprite(int SIZE, int x, SpriteSheet sheet, int y, int realWidth, int readHeight) {
    this.SIZE = SIZE;
    this.x = x;
    this.y = y;
    this.realWidth = realWidth;
    this.readHeight = readHeight;
    this.sheet = sheet;
    pixels = new int[SIZE * SIZE];
  }

  public static Sprite movingSprite(Sprite bomb, Object bomb1, Object bomb2, int i) {

  }

  private void setColor(int color) {
    for (int i = 0; i < pixels.length; i++) {
      pixels[i] = color;
    }
  }

  private void load() {
    // load dữ liệu từ pixels trong SpriteSheet sang pixels trong Sprite
    for (int y = 0; y < SIZE; y++) {
      for (int x = 0; x < SIZE; x++) {
        pixels[x + y * SIZE] = sheet.pixels[(this.x + x) + (this.y + y) * sheet.SIZE];
      }
    }
  }

  public int getPixel(int i) {
    return pixels[i];
  }

  public static Sprite voidSprite = new Sprite(16, 0xffffff); // black

  public double getSize() {
  }
}
