package Breakout;/**
 * Created by Romano on 01.07.2016.
 */

import com.almasb.fxgl.GameApplication;
import com.almasb.fxgl.GameSettings;
import com.almasb.fxgl.asset.Assets;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityType;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsEntity;
import com.almasb.fxgl.physics.PhysicsManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import static javafx.application.Application.launch;

public class Breakout extends GameApplication{

    private Assets assets;

    private PhysicsEntity bat, ball, extraball;

    private IntegerProperty score = new SimpleIntegerProperty();

    private IntegerProperty life = new SimpleIntegerProperty();

    private enum Type implements EntityType{
        BAT, BALL, BRICK, WALL
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Breakout");
        settings.setVersion("dev");
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setIntroEnabled(false);
    }

    // Steuerung des Paddles mit "A" und "D"
    @Override
    protected void initInput() {
        inputManager.addKeyPressBinding(KeyCode.A, () ->{
            Point2D position;
            position = bat.getPosition();
            if(position.getX() < 25 ) {
                bat.setLinearVelocity(0,0);
            }
            else{
                bat.setLinearVelocity(-5,0);
            }

        });

        inputManager.addKeyPressBinding(KeyCode.D, () -> {
            Point2D position;
            position = bat.getPosition();
            if(position.getX() > 1125){
                bat.setLinearVelocity(0,0);
            }
            else {
                bat.setLinearVelocity(5, 0);
            }
        });
    }

    @Override
    protected void initAssets() throws Exception {
        assets = assetManager.cache();
        assets.logCached();
    }

    @Override
    protected void initGame() {

        physicsManager.setGravity(0,0);
        initWalls();
        initBat();
        initBall("ball_red.png");
        initBrick();
        initBackground();
        setLife(3);

        //Kollisionsabfrage zw. Ball und Brick
        physicsManager.addCollisionHandler(new CollisionHandler(Type.BALL, Type.BRICK) {
            @Override
            public void onCollisionBegin(Entity a, Entity b) {
                removeEntity(b);
                score.set(score.get() + 100);
            }
            @Override
            public void onCollision(Entity a, Entity b) {
            }
            @Override
            public void onCollisionEnd(Entity a, Entity b) {
            }
        });

        physicsManager.addCollisionHandler(new CollisionHandler(Type.BALL, Type.WALL) {
            @Override
            public void onCollisionBegin(Entity a, Entity b) {
                //Was passiert wenn der Ball den Boden berührt?
                score.set(score.get() - 1000);
                life.set(life.get()-1);

            }
            @Override
            public void onCollision(Entity a, Entity b) {
            }
            @Override
            public void onCollisionEnd(Entity a, Entity b) {
            }
        });

    }

    private void initBackground() {



    }

    private void initWalls() {
        for (int i = 0; i < 60; i++){
            PhysicsEntity top = new PhysicsEntity(Type.WALL);
            top.setPosition((i%50)*28-8, -8);
            top.setGraphics(assets.getTexture("Walls/brick_red.png"));

            addEntities(top);
        }
        for (int i = 0; i < 60; i++){
            PhysicsEntity left = new PhysicsEntity(Type.WALL);
            left.setPosition(-8, (i%50)*28-8);
            left.setGraphics(assets.getTexture("Walls/brick_red.png"));

            addEntities(left);
        }
        for (int i = 0; i < 60; i++){
            PhysicsEntity right = new PhysicsEntity(Type.WALL);
            right.setPosition(getWidth()-28, (i%50)*28-8);
            right.setGraphics(assets.getTexture("Walls/brick_red.png"));

            addEntities(right);
        }

        PhysicsEntity bot = new PhysicsEntity(Type.WALL);
        bot.setPosition(0,getHeight());
        bot.setGraphics(new Rectangle(getWidth(),8));
        bot.setCollidable(true);
        addEntities(bot );


    }
    @Override
    protected void initUI(Pane uiRoot) {
        Text scoreText = new Text();
        scoreText.setTranslateY(20);
        scoreText.setTranslateX(5);
        scoreText.setFont(Font.font(20));
        scoreText.textProperty().bind(score.asString());

        uiRoot.getChildren().add(scoreText);

        Text lifeText = new Text();
        lifeText.setTranslateY(20);
        lifeText.setTranslateX(1260);
        lifeText.setFont(Font.font(20));
        lifeText.textProperty().bind(life.asString());

        uiRoot.getChildren().add(lifeText);
    }

    private void initBat() {
        bat = new PhysicsEntity(Type.BAT);
        bat.setPosition(getWidth()/2 - 135 / 2, getHeight()- 32);
        bat.setGraphics(assets.getTexture("Bats/bat_black.png"));
        bat.setBodyType(BodyType.KINEMATIC);

        addEntities(bat);

    }

    private void initBall(String Color) {
        ball = new PhysicsEntity(Type.BALL);
        ball.setPosition(getWidth()/2 - 35 / 2, getHeight()/2 - 35 / 2);
        ball.setGraphics(assets.getTexture("Balls/"+Color));
        ball.setBodyType(BodyType.DYNAMIC);
        ball.setCollidable(true);

        FixtureDef fd = new FixtureDef();
        fd.restitution = 0.8f;
        fd.shape = new CircleShape();
        fd.shape.setRadius(PhysicsManager.toMeters(13.5));
        ball.setFixtureDef(fd);
        addEntities(ball);

        ball.setLinearVelocity(5,-10 );

    }

    private void initBrick(){
        for (int i = 0; i < 60; i++){
            PhysicsEntity brick = new PhysicsEntity(Type.BRICK);
            brick.setPosition((i % 11) *100 + 95, (i / 16)* 45 + 30);
            brick.setGraphics(assets.getTexture("Bricks/brick_blue_small.png"));
            brick.setCollidable(true);

            addEntities(brick);
        }
    }

    @Override
    protected void onUpdate() {
        bat.setLinearVelocity(0,0);

        //Geschwindigkeit des Balls regeln. Wenn Geschw. unter 5 -> auf 5 setzen
        Point2D v = ball.getLinearVelocity();
        if(Math.abs(v.getY())<5){
            double x = v.getX();
            double signY = Math.signum(v.getY());
            ball.setLinearVelocity(x, signY * 5 );
        }
    }

    //Extra welches einen zweiten Ball erzeugt, zu erkennen an einer anderen Farbe
    private void extraBall(String Color){
        ball = new PhysicsEntity(Type.BALL);
        ball.setPosition(getWidth()/2 - 35 / 2, getHeight()/2 - 35 / 2);
        ball.setGraphics(assets.getTexture("Balls/"+Color));
        ball.setBodyType(BodyType.DYNAMIC);
        ball.setCollidable(true);

        FixtureDef fd = new FixtureDef();
        fd.restitution = 0.8f;
        fd.shape = new CircleShape();
        fd.shape.setRadius(PhysicsManager.toMeters(13.5));
        ball.setFixtureDef(fd);
        addEntities(ball);

        ball.setLinearVelocity(5,-10 );
    }


    private void setLife(int value){
        life.set(value);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
