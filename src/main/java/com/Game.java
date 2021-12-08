package com;

import com.GUI.Frame;
import com.Graphics.Screen;

public class Game {

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

    private void update() {
        input.update();
        board.update();
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
