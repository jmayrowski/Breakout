package Breakout;

import com.almasb.ents.AbstractControl;
import com.almasb.ents.Entity;
import com.almasb.fxgl.entity.component.BoundingBoxComponent;
import com.almasb.fxgl.entity.component.PositionComponent;
import com.almasb.fxgl.physics.PhysicsComponent;

/**
 * Created by Romano on 09.07.2016.
 */
public class BatControl extends AbstractControl {

    protected PositionComponent position;
    protected PhysicsComponent bat;
    protected BoundingBoxComponent bbox;

    @Override
    public void onAdded(Entity entity) {
        bat = entity.getComponentUnsafe(PhysicsComponent.class);
        position = entity.getComponentUnsafe(PositionComponent.class);
        bbox = entity.getComponentUnsafe(BoundingBoxComponent.class);
    }

    @Override
    public void onUpdate(Entity entity, double tpf) {}

    public void left() {

            bat.setLinearVelocity(-5, 0);

    }

    public void right() {

            bat.setLinearVelocity(5, 0);

    }

    public void stop() {
        bat.setLinearVelocity(0, 0);
    }
}