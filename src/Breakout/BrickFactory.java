package Breakout;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import org.jbox2d.dynamics.BodyType;

import java.lang.String;

/**
 * Created by Romano on 09.07.2016.
 */
public class BrickFactory {

    private BrickFactory brickFactory;
    private String textureName;
    private GameEntity brick;
    private static PhysicsComponent brickPhysics;


    public BrickFactory getBrickFactory() {
        return brickFactory;
    }

    public void setBrickFactory(BrickFactory brickFactory) {
        this.brickFactory = brickFactory;
    }

    public String chooseTextureColor(int colorKey){

        switch (colorKey) {
            case 0:  textureName = "Bricks/brick_blue_small.png";
                break;
            case 1:  textureName = "Bricks/brick_blue_small_cracked.png";
                break;
            case 2:  textureName = "Bricks/brick_green_small.png";
                break;
            case 3:  textureName = "Bricks/brick_green_small_cracked.png";
                break;
            case 4:  textureName = "Bricks/brick_pink_small.png";
                break;
            case 5:  textureName = "Bricks/brick_pink_small_cracked.png";
                break;
            case 6:  textureName = "Bricks/brick_violet_small.png";
                break;
            case 7:  textureName = "Bricks/brick_violet_small_cracked.png";
                break;
            case 8:  textureName = "Bricks/brick_yellow_small.png";
                break;
            case 9:  textureName = "Bricks/brick_yellow_small_cracked.png";

                break;
        }

        return textureName;
    }

    public GameEntity initBrick(int position, int colorKey){

        BoundingShape Box = BoundingShape.box(90,40);

        Point2D p = new Point2D((position % 11) * 100 + 95, (position / 16) * 45 + 30);

        brick = Entities.builder()
                .type(Breakout.Type.BRICK)
                .at(p.getX(),p.getY())
                .bbox(new HitBox("BrickHitBox", Box))
                .viewFromTextureWithBBox(chooseTextureColor(colorKey))
                .build();

        brickPhysics = new PhysicsComponent();
        brickPhysics.setBodyType(BodyType.STATIC);

        brick.addComponent(brickPhysics);
        brick.addComponent(new CollidableComponent(true));

        return brick;
    }
}


