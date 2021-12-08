package com.Levels;

import com.Board;
import com.Entities.Mobs.Player;
import com.Entities.Tiles.BrickTile;
import com.Entities.Tiles.GrassTile;
import com.Entities.Tiles.PortalTile;
import com.Entities.Tiles.Powerups.PowerupBombs;
import com.Entities.Tiles.Powerups.PowerupFlames;
import com.Entities.Tiles.Powerups.PowerupSpeed;
import com.Entities.Tiles.WallTile;
import com.Entities.LayeredEntity;
import com.Game;
import com.Graphics.Screen;
import com.Graphics.Sprite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

public class FileLevel extends Level {

    public FileLevel(String path, Board board) throws LoadLevelException {
        super(path, board);
    }

    @Override
    public void loadLevel(String path) throws LoadLevelException {
        try {
            URL absPath = FileLevel.class.getResource("/" + path);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(absPath.openStream()));

            String data = in.readLine();
            StringTokenizer tokens = new StringTokenizer(data);

            level = Integer.parseInt(tokens.nextToken());
            height = Integer.parseInt(tokens.nextToken());
            width = Integer.parseInt(tokens.nextToken());

            lineTiles = new String[height];

            for(int i = 0; i < height; ++i) {
                lineTiles[i] = in.readLine().substring(0, width);
            }

            in.close();
        } catch (IOException e) {
            throw new LoadLevelException("Error loading level " + path, e);
        }
    }

    @Override
    public void createEntities() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                addLevelEntity( lineTiles[y].charAt(x), x, y );
            }
        }
    }

    public void addLevelEntity(char c, int x, int y) {
        int pos = x + y * getWidth();

        switch(c) { // TODO: minimize this method
            case '#':
                board.addEntitie(pos, new WallTile(x, y, Sprite.wall));
                break;
            case 'b':
                LayeredEntity layer = new LayeredEntity(x, y,
                        new GrassTile(x ,y, Sprite.grass),
                        new BrickTile(x ,y, Sprite.brick));

                if(board.isPowerupUsed(x, y, level) == false) {
                    layer.addBeforeTop(new PowerupBombs(x, y, level, Sprite.powerupbombs));
                }

                board.addEntitie(pos, layer);
                break;
            case 's':
                layer = new LayeredEntity(x, y,
                        new GrassTile(x ,y, Sprite.grass),
                        new BrickTile(x ,y, Sprite.brick));

                if(board.isPowerupUsed(x, y, level) == false) {
                    layer.addBeforeTop(new PowerupSpeed(x, y, level, Sprite.powerupspeed));
                }

                board.addEntitie(pos, layer);
                break;
            case 'f':
                layer = new LayeredEntity(x, y,
                        new GrassTile(x ,y, Sprite.grass),
                        new BrickTile(x ,y, Sprite.brick));

                if(board.isPowerupUsed(x, y, level) == false) {
                    layer.addBeforeTop(new PowerupFlames(x, y, level, Sprite.powerupflames));
                }

                board.addEntitie(pos, layer);
                break;
            case '*':
                board.addEntitie(pos, new LayeredEntity(x, y,
                        new GrassTile(x ,y, Sprite.grass),
                        new BrickTile(x ,y, Sprite.brick)) );
                break;
            case 'x':
                board.addEntitie(pos, new LayeredEntity(x, y,
                        new GrassTile(x ,y, Sprite.grass),
                        new PortalTile(x ,y, board, Sprite.portal),
                        new BrickTile(x ,y, Sprite.brick)) );
                break;
            case ' ':
                board.addEntitie(pos, new GrassTile(x, y, Sprite.grass) );
                break;
            case 'p':
                board.addMob( new Player(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILESSIZE, board) );
                Screen.setOffset(0, 0);

                board.addEntitie(pos, new GrassTile(x, y, Sprite.grass) );
                break;
            //Enemies
            case '1':
                board.addMob( new Balloom(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILESSIZE, board));
                board.addEntitie(pos, new GrassTile(x, y, Sprite.grass) );
                break;
            case '2':
                board.addMob( new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILESSIZE, board));
                board.addEntitie(pos, new GrassTile(x, y, Sprite.grass) );
                break;
            case '3':
                board.addMob( new Doll(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILESSIZE, board));
                board.addEntitie(pos, new GrassTile(x, y, Sprite.grass) );
                break;
            case '4':
                board.addMob( new Minvo(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILESSIZE, board));
                board.addEntitie(pos, new GrassTile(x, y, Sprite.grass) );
                break;
            case '5':
                board.addMob( new Kondoria(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILESSIZE, board));
                board.addEntitie(pos, new GrassTile(x, y, Sprite.grass) );
                break;
            default:
                board.addEntitie(pos, new GrassTile(x, y, Sprite.grass) );
                break;
        }
    }
}
