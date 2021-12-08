package com.Levels;

import com.Board;

public abstract class Level implements ILevel {

    protected int width, height, level;
    protected String[] lineTiles;
    protected Board board;

    protected static String[] codes = { //TODO: change this code system to actualy load the code from each level.txt
            "dnibpb5uqy",
            "cuq0vaxstb",
            "38y418wriq",
            "34h8k0chcs",
            "9qztxh6l4s",
    };

    public Level(String path, Board board) throws LoadLevelException {
        loadLevel(path);
        board = board;
    }

    @Override
    public abstract void loadLevel(String path) throws LoadLevelException;

    public abstract void createEntities();

    public int validCode(String str) {
        for (int i = 0; i < codes.length; i++) {
            if (codes[i].equals(str)) {
                return i;
            }
        }
        return -1;
    }

    public String getActualCode() {
        return codes[level -1];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLevel() {
        return level;
    }
}
