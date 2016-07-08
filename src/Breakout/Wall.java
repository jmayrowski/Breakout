package Breakout;

/**
 * Created by Romano on 06.07.2016.
 */
public class Wall {

    public Wall(){

    }

    private Breakout b;


    protected void initWalls(){


        /*b = new Breakout();

        for (int i = 0; i < 60; i++){
            PhysicsEntity top = new PhysicsEntity(Type.WALL);
            top.setPosition((i%50)*28-8, -8);
            top.setGraphics(Breakout.assets.getTexture("Walls/brick_red.png"));

            b.addEntities(top);
        }
        for (int i = 0; i < 60; i++){
            PhysicsEntity left = new PhysicsEntity(Type.WALL);
            left.setPosition(-8, (i%50)*28-8);
            left.setGraphics(Breakout.assets.getTexture("Walls/brick_red.png"));

            b.addEntities(left);
        }
        for (int i = 0; i < 60; i++){
            PhysicsEntity right = new PhysicsEntity(Type.WALL);
            right.setPosition(b.getWidth()-28, (i%50)*28-8);
            right.setGraphics(Breakout.assets.getTexture("Walls/brick_red.png"));

            b.addEntities(right);
        }

        PhysicsEntity bot = new PhysicsEntity(Type.WALL);
        bot.setPosition(0,b.getHeight());
        bot.setGraphics(new Rectangle(b.getWidth(),8));
        bot.setCollidable(true);
        b.addEntities(bot );
    }*/
}}
