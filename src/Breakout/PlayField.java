package Breakout;

import com.almasb.fxgl.entity.GameEntity;

import java.util.ArrayList;

/**
 * Created by Romano on 09.07.2016.
 */
public class PlayField {

    private GameEntity brick0;
    private GameEntity brick1;
    private GameEntity brick2;
    private GameEntity brick3;
    private GameEntity brick4;
    private GameEntity brick5;
    private GameEntity brick6;
    private GameEntity brick7;
    private GameEntity brick8;
    private GameEntity brick9;
    private GameEntity brick10;
    private GameEntity brick11;

    private GameEntity playField;

    public ArrayList<GameEntity> getPlayField(){

        BrickFactory bf = new BrickFactory();

        brick0 = bf.initBrick(0,7);
        brick1 = bf.initBrick(1,1);
        brick2 = bf.initBrick(2,0);
        brick3 = bf.initBrick(3,0);
        brick4 = bf.initBrick(4,0);
        brick5 = bf.initBrick(5,0);
        brick6 = bf.initBrick(6,0);
        brick7 = bf.initBrick(7,0);
        brick8 = bf.initBrick(8,0);
        brick9 = bf.initBrick(9,0);
        brick10 = bf.initBrick(83,5);
        brick11 = bf.initBrick(60,3);


        ArrayList<GameEntity> playField = new ArrayList<GameEntity>();

        playField.add(brick0);
        playField.add(brick1);
        playField.add(brick2);
        playField.add(brick3);
        playField.add(brick4);
        playField.add(brick5);
        playField.add(brick6);
        playField.add(brick7);
        playField.add(brick8);
        playField.add(brick9);
        playField.add(brick10);
        playField.add(brick11);

        return playField;
    }
}
