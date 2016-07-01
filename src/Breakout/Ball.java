package Breakout;

import java.awt.*;

/**
 * Created by Romano on 01.07.2016.
 */
public class Ball {

    private Breakout game;
    private int radius = 10;
    Point position = new Point(0,0);
    Point movement = new Point(1,1);
    float speed = 0.3f;


    public Ball(Breakout game){
         this.game = game;
    }
    public void tick(double deltatime){
        position.translate((int)(movement.x*(speed*deltatime)), (int)(movement.y*(speed*deltatime)));

        if (Math.abs(position.x) > Math.abs(game.width/2)-radius*2) movement.x = -movement.x ;
        if (Math.abs(position.y) > Math.abs(game.height/2)-radius*2) movement.y = -movement.y;

        Rectangle playerHitbox = new Rectangle(game.player.position.x-game.player.width/2, game.player.position.y-game.player.height/2, game.player.width, game.player.height);
        Rectangle ballHitbox = new Rectangle(position.x - radius/2, position.y - radius/2, radius*2, radius*2);
        if(playerHitbox.intersects(ballHitbox))movement.y = -movement.y;
    }

    public void render(Graphics g){

        g.setColor(Color.RED);
        g.fillOval(position.x - radius, position.y - radius, radius*2, radius*2);
    }
}
