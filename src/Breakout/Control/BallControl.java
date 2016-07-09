package Breakout.Control;

import com.almasb.ents.AbstractControl;
import com.almasb.ents.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

/**
 * Created by Romano on 09.07.2016.
 */
public class BallControl extends AbstractControl {

    private PhysicsComponent ball;

    @Override
    public void onAdded(Entity entity) {
        ball = entity.getComponentUnsafe(PhysicsComponent.class);
    }

    @Override
    public void onUpdate(Entity entity, double tpf) {
        Point2D v = ball.getLinearVelocity();
        if (Math.abs(v.getY()) < 5) {
            double x = v.getX();
            double signY = Math.signum(v.getY());
            ball.setLinearVelocity(x, signY * 5);
        } else {

            if (Math.abs(ball.getLinearVelocity().getX()) < 5) {
                ball.setLinearVelocity(Math.signum(ball.getLinearVelocity().getX()) * 5,
                        ball.getLinearVelocity().getY());
            }
        }
    }
}
