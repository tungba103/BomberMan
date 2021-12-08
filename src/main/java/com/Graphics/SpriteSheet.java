package com.Graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
// Lấy texture từ file classic.png xuất vào biến tính tiles để sử dụng cho Sprite
public class SpriteSheet {

  private String path;
  protected final int SIZE;
  protected int[] pixels;

  public static SpriteSheet tiles = new SpriteSheet("/Textures/classic.png", 256);

  private SpriteSheet(String path, int SIZE) {
    this.path = path;
    this.SIZE = SIZE;
    pixels = new int[SIZE * SIZE];
    load();
  }

  private void load() {
    try {
      URL a = SpriteSheet.class.getResource(path);
      BufferedImage image = ImageIO.read(a);
      int w = image.getWidth();
      int h = image.getHeight();
      image.getRGB(0, 0, w, h, pixels, 0, w);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(0);
    }
  }

}
