package Breakout;

import java.awt.*;

/**
 * Created by Romano on 01.07.2016.
 */
public class Player {
    private Breakout game;
    int width = 100;
    int height = 10;
    Point position = new Point(0,0);

    public Player(Breakout game){
        this.game = game;
        position = new Point(0, game.height/2- height- 20 );
    }

    public void render(Graphics g){
        g.setColor(Color.lightGray);
        g.fillRect(position.x - width/2, position.y - height/2, width, height);
    }
}
