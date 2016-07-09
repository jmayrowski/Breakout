package Breakout;

import Breakout.Control.BallControl;
import com.almasb.ents.Entity;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import static com.almasb.fxgl.physics.BoundingShape.box;

/**
 * Created by Romano on 09.07.2016.
 */
public class BallFactory {

    public GameEntity ball;

    public GameEntity getBall() {
        return ball;
    }



    public static PhysicsComponent ballPhysics;


    public Entity createBall(double x, double y){

        BoundingShape Ball = box(27,27);


        ball = Entities.builder()
                .type(Breakout.Type.BALL)
                .at(x,y)
                .bbox(new HitBox("BallHitBox", Ball))
                .viewFromTextureWithBBox("Balls/ball_red.png")
                .build();


        ball.addComponent(new CollidableComponent(true));

        ballPhysics = new PhysicsComponent();
        ballPhysics.setBodyType(BodyType.DYNAMIC);


        FixtureDef fd = new FixtureDef();
        fd.setRestitution(0.8f);
        fd.setShape(new CircleShape());
        fd.getShape().setRadius((float) 13.5);
        ballPhysics.setFixtureDef(fd);



        ball.addComponent(ballPhysics);

        ball.addControl(new BallControl());
        return ball;
    }

}


