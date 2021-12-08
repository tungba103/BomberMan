package com.Entities.Tiles;

import com.Entities.Entity;
import com.Entities.Mobs.Player;
import com.Board;
import com.Graphics.Sprite;

public class PortalTile extends Tile{

    protected Board board;

    public PortalTile(int x, int y, Board board, Sprite sprite) {
        super(x, y, sprite);
        this.board = board;
    }

    @Override
    public boolean collide(Entity e) {

        if(e instanceof Player) {

            if(!board.detectNoEnemies())
                return false;

            if(e.getXTile() == getX() && e.getYTile() == getY()) {
                if(board.detectNoEnemies())
                    board.nextLevel();
            }

            return true;
        }

        return false;
    }
}
