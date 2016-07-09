package Breakout;

import com.almasb.ents.Entity;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jbox2d.dynamics.BodyType;

import static com.almasb.fxgl.physics.BoundingShape.box;

/**
 * Created by Romano on 06.07.2016.
 */
public class WallFactory {

    public WallFactory(){

    }
    private GameEntity wall;
    private static PhysicsComponent wallPhysics;

    public Entity createWalls(String location, double  width, double height, int i){

        BoundingShape Box = box(40,40);

        if(location == "top") {

            GameEntity top = Entities.builder()
                    .type(Breakout.Type.WALL)
                    .at((i % 50) * 28 - 8, -8)
                    .bbox(new HitBox("WallHitBox", Box))
                    .viewFromTextureWithBBox("Walls/brick_red.png")
                    .build();

            top.addComponent(new CollidableComponent(true));

            wallPhysics = new PhysicsComponent();
            wallPhysics.setBodyType(BodyType.STATIC);

            top.addComponent(wallPhysics);

            wall = top;


        }

        if(location == "left") {

            GameEntity left = Entities.builder()
                        .type(Breakout.Type.WALL)
                        .at(-8, (i % 50) * 28 - 8)
                        .bbox(new HitBox("WallHitBox", Box))
                        .viewFromTextureWithBBox("Walls/brick_red.png")
                        .build();

            wallPhysics = new PhysicsComponent();
            wallPhysics.setBodyType(BodyType.STATIC);

            left.addComponent(wallPhysics);
            left.addComponent(new CollidableComponent(true));

            wall = left;


        }

        if(location == "right") {

            GameEntity right = Entities.builder()
                        .type(Breakout.Type.WALL)
                        .at(width - 28, (i % 50) * 28 - 8)
                        .bbox(new HitBox("WallHitBox", Box))
                        .viewFromTextureWithBBox("Walls/brick_red.png")
                        .build();

            wallPhysics = new PhysicsComponent();
            wallPhysics.setBodyType(BodyType.STATIC);

            right.addComponent(wallPhysics);
            right.addComponent(new CollidableComponent(true));

            wall = right;

        }

        if(location == "bot"){

            GameEntity bot = Entities.builder()
                    .type(Breakout.Type.GROUND)
                    .at(0, height)
                    .bbox(new HitBox("GroundHitBox", box(width,  200)))
                    .viewFromNodeWithBBox(new Rectangle(width,  height - 200, Color.RED))
                    .build();

            wallPhysics = new PhysicsComponent();
            wallPhysics.setBodyType(BodyType.STATIC);

            bot.addComponent(wallPhysics);
            bot.addComponent(new CollidableComponent(true));

            wall = bot;
        }


        return wall;
    }
}
