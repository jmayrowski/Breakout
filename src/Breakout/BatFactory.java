package Breakout;

import Breakout.Control.BatControl;
import com.almasb.ents.Entity;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import org.jbox2d.dynamics.BodyType;

import static com.almasb.fxgl.physics.BoundingShape.box;

/**
 * Created by Romano on 09.07.2016.
 */
public class BatFactory {

    private GameEntity bat;

    public GameEntity getBat() {
        return bat;
    }

    public static PhysicsComponent batPhysics;


    public Entity createBat(double x, double y){
        BoundingShape Box = box(135,30);

        //HitBox hitBox = new HitBox("PlayerHitBox", new BoundingBox(0, 0, 135, 30))
        //Entity erstellen
        bat = Entities.builder()
                .type(Breakout.Type.BAT)
                .at(x,y)
                .rotate(0)
                .bbox(new HitBox("BatHitBox", Box))
                .viewFromTextureWithBBox("Bats/bat_black.png")
                .build();

        batPhysics = new PhysicsComponent();
        batPhysics.setBodyType(BodyType.KINEMATIC);

        bat.addComponent(batPhysics);
        bat.addComponent(new CollidableComponent(true));
        bat.addControl(new BatControl());

        return bat;
    }
}
