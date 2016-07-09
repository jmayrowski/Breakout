package Breakout;/**
 * Created by Romano on 01.07.2016.
 */


import Breakout.Control.BallControl;
import Breakout.Control.BatControl;
import com.almasb.ents.Entity;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.entity.component.TypeComponent;
import com.almasb.fxgl.input.ActionType;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.InputMapping;
import com.almasb.fxgl.input.OnUserAction;
import com.almasb.fxgl.physics.*;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.texture.Texture;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import static com.almasb.fxgl.physics.BoundingShape.box;


public class Breakout extends GameApplication{

    /*public static Assets assets;



    public Assets getAssets() {
        return assets;
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }*/

    /*private PhysicsComponent bat;
    private PhysicsComponent ball;*/

    private GameEntity bat;
    private GameEntity ball;

    private PhysicsComponent batPhysics;
    private PhysicsComponent ballPhysics;

    private Texture batTexture;
    private Texture ballTexture;
    private Texture brickTexture;
    private Texture wallTexture;

    private Text scoreText;

    private BatControl batControl;

    private IntegerProperty score = new SimpleIntegerProperty();
    public enum Type {
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

    @Override
    protected void initInput() {

        Input input = getInput();

        input.addInputMapping(new InputMapping("left", KeyCode.A));

        input.addInputMapping(new InputMapping("right", KeyCode.D));

        /*input.addAction(new UserAction("Left") {
            @Override
            protected void onActionBegin() {
            batControl.left();
            }
        }), KeyCode.A;


        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onActionBegin() {
                batControl.right();
            }
        }), KeyCode.D;*/
    }
    @OnUserAction(name = "left", type = ActionType.ON_ACTION)
        public void left() {
        batControl.left();
    }

    @OnUserAction(name = "right", type = ActionType.ON_ACTION)
        public  void right(){
        batControl.right();
    }

    @OnUserAction(name = "left", type = ActionType.ON_ACTION_END)
    public void stopBat() {
        batControl.stop();
    }

    @OnUserAction(name = "right", type = ActionType.ON_ACTION_END)
    public void stopBat2() {
        batControl.stop();
    }
        /*inputManager.addKeyPressBinding(KeyCode.A, () ->{
            bat.setLinearVelocity(-5,0);
        });

        inputManager.addKeyPressBinding(KeyCode.D, () -> {
            bat.setLinearVelocity(5, 0);
        });*/

    /*private GameEntity createPhysicsEntity(int x) {
        // 1. create and configure physics component
        PhysicsComponent physics = new PhysicsComponent();

        physics.setBodyType(BodyType.KINEMATIC);
        physics.setLinearVelocity(x,0);

        FixtureDef fd = new FixtureDef();
        fd.setDensity(0.7f);
        fd.setRestitution(0.3f);
        physics.setFixtureDef(fd);

        return Entities.builder()
                // 2. add physics component
                .with(physics)
                .build();
    }*/


    @Override
    protected void initAssets() {


        ballTexture = getAssetLoader().loadTexture("Balls/ball_red.png");
        batTexture = getAssetLoader().loadTexture("Bats/bat_black.png");
        brickTexture = getAssetLoader().loadTexture("Bricks/brick_blue_small.png");
        wallTexture = getAssetLoader().loadTexture("Walls/brick_red.png");

        //getAssetLoader().cache();
        /*setAssets(assetManager.cache());

        getAssets().logCached();*/
    }

    @Override
    protected void initGame() {

        //physicsManager.setGravity(0,0);
        //Wall wall = new Wall();
        //wall.initWalls();
        //initWalls();
        initBat();
        initBall();
        initBrick();
        initBackground();
        initScreenBounds();



    }

    @Override
    protected void initPhysics() {
        //Kollisionsabfrage zw. Ball und Brick
        PhysicsWorld physics = getPhysicsWorld();

        //physics.setGravity(0,0);
        physics.addCollisionHandler(new CollisionHandler(Type.BALL, Type.BRICK) {
            @Override
            public void onCollisionBegin(Entity a, Entity b) {
                b.removeFromWorld();
                score.set(score.get() + 100);
            }
            @Override
            public void onCollision(Entity a, Entity b) {
            }
            @Override
            public void onCollisionEnd(Entity a, Entity b) {
            }
        });

        physics.addCollisionHandler(new CollisionHandler(Type.BALL, Type.WALL) {
            @Override
            public void onCollisionBegin(Entity a, Entity b) {
                //Was passiert wenn der Ball den Boden berührt?
                score.set(score.get() - 1000);
            }
            @Override
            public void onCollision(Entity a, Entity b) {
            }
            @Override
            public void onCollisionEnd(Entity a, Entity b) {
            }
        });
    }

    @Override
    protected void initUI() {

        scoreText = new Text();

        scoreText.setTranslateY(20);
        scoreText.setTranslateX(5);
        scoreText.setFont(Font.font(20));
        scoreText.textProperty().bind(score.asString());

        getGameScene().addUINode(scoreText);
    }

    private void initBackground() {



    }

    private void initWalls() {

        BoundingShape Box = box(40,40);
        for (int i = 0; i < 60; i++){

            GameEntity top = Entities.builder()
                    .type(Type.WALL)
                    .at((i%50)*28-8, -8)
                    .viewFromTextureWithBBox("Walls/brick_red.png")
                    .build();

            /*top.getTypeComponent().setValue(Type.WALL);
            top.getPositionComponent().setValue((i%50)*28-8, -8);
            top.getMainViewComponent().setTexture("Walls/brick_red.png", true);
            top.getBoundingBoxComponent().addHitBox(new HitBox("WallHitBox", Box));*/

            top.addComponent(new CollidableComponent(true));

            getGameWorld().addEntities(top);

            /*PhysicsEntity top = new PhysicsEntity(Type.WALL);
            top.setPosition((i%50)*28-8, -8);
            top.setGraphics(getAssets().getTexture("Walls/brick_red.png"));

            addEntities(top);*/
        }
        for (int i = 0; i < 60; i++){

            GameEntity left = new GameEntity();
            left.getTypeComponent().setValue(Type.WALL);
            left.getPositionComponent().setValue(-8, (i%50)*28-8);
            left.getMainViewComponent().setTexture("Walls/brick_red.png", true);
            //left.addComponent(new CollidableComponent(true));

            getGameWorld().addEntities(left);

            /*PhysicsEntity left = new PhysicsEntity(Type.WALL);
            left.setPosition(-8, (i%50)*28-8);
            left.setGraphics(getAssets().getTexture("Walls/brick_red.png"));

            addEntities(left);*/
        }
        for (int i = 0; i < 60; i++){

            GameEntity right = new GameEntity();
            right.getTypeComponent().setValue(Type.WALL);
            right.getPositionComponent().setValue(getWidth()-28, (i%50)*28-8);
            right.getMainViewComponent().setTexture("Walls/brick_red.png", true);
            //right.addComponent(new CollidableComponent(true));

            getGameWorld().addEntities(right);

            /*PhysicsEntity right = new PhysicsEntity(Type.WALL);
            right.setPosition(getWidth()-28, (i%50)*28-8);
            right.setGraphics(getAssets().getTexture("Walls/brick_red.png"));

            addEntities(right);*/
        }

        GameEntity bot = new GameEntity();
        bot.getTypeComponent().setValue(Type.WALL);
        bot.getPositionComponent().setValue(0,getHeight());
        bot.getMainViewComponent().setView(new Rectangle(getWidth(),8));
        bot.addComponent(new CollidableComponent(true));

        getGameWorld().addEntities(bot);

        /*PhysicsEntity bot = new PhysicsEntity(Type.WALL);
        bot.setPosition(0,getHeight());
        bot.setGraphics(new Rectangle(getWidth(),8));
        bot.setCollidable(true);
        addEntities(bot );*/


    }


    /*@Override
    protected void initUI(Pane uiRoot) {
        Text scoreText = new Text();
        scoreText.setTranslateY(20);
        scoreText.setTranslateX(5);
        scoreText.setFont(Font.font(20));
        scoreText.textProperty().bind(score.asString());

        uiRoot.getChildren().add(scoreText);
    }*/

    private void initBat() {

        BoundingShape Box = box(135,30);

        //HitBox hitBox = new HitBox("PlayerHitBox", new BoundingBox(0, 0, 135, 30))
        //Entity erstellen
        bat = Entities.builder()
                .type(Type.BAT)
                .at(getWidth() / 2 - 135 / 2, getHeight() - 32)
                .rotate(1)
                //.bbox(new HitBox("BatHitBox", Box))
                .viewFromTextureWithBBox("Bats/bat_black.png")
                .build();

        batPhysics = new PhysicsComponent();
        batPhysics.setBodyType(BodyType.KINEMATIC);

        bat.addComponent(batPhysics);
        bat.addComponent(new CollidableComponent(true));
        bat.addControl(new BatControl());
        batControl = bat.getControlUnsafe(BatControl.class );
        getGameWorld().addEntities(bat);





        /*bat = new PhysicsEntity(Type.BAT);
        bat.setPosition(getWidth()/2 - 135 / 2, getHeight()- 32);
        bat.setGraphics(getAssets().getTexture("Bats/bat_black.png"));
        bat.setBodyType(BodyType.KINEMATIC);
        addEntities(bat);*/
    }

    private void initBall() {

        //HitBox ballHitBox = new HitBox(new Rectangle(27,27));
        BoundingShape Ball = box(27,27);

        ball = Entities.builder()
                .type(Type.BALL)
                .at(getWidth()/2 - 35 / 2, getHeight()/2 - 35 / 2)
                .bbox(new HitBox("BallHitBox", Ball))
                .viewFromTextureWithBBox("Balls/ball_red.png")
                .build();
        /*ball = new GameEntity();
        ball.getTypeComponent().setValue(Type.BALL);
        ball.getPositionComponent().setValue(getWidth()/2 - 35 / 2, getHeight()/2 - 35 / 2);
        ball.getMainViewComponent().setTexture("Balls/ball_red.png", true);
        ball.getBoundingBoxComponent().addHitBox(new HitBox("BallHitBox",Ball));*/

        ball.addComponent(new CollidableComponent(true));

        ballPhysics = new PhysicsComponent();
        ballPhysics.setBodyType(BodyType.DYNAMIC);


        FixtureDef fd = new FixtureDef();
        fd.setRestitution(0.8f);
        fd.setShape(new CircleShape());
        fd.getShape().setRadius(13);
        ballPhysics.setFixtureDef(fd);



        ball.addComponent(ballPhysics);

        ball.addControl(new BallControl());


        getGameWorld().addEntities(ball);

        /*ball = new PhysicsEntity(Type.BALL);
        ball.setPosition(getWidth()/2 - 35 / 2, getHeight()/2 - 35 / 2);
        ball.setGraphics(getAssets().getTexture("Balls/ball_red.png"));
        ball.setBodyType(BodyType.DYNAMIC);
        ball.setCollidable(true);

        FixtureDef fd = new FixtureDef();
        fd.restitution = 0.8f;
        fd.shape = new CircleShape();
        fd.shape.setRadius(PhysicsManager.toMeters(13.5));
        ball.setFixtureDef(fd);
        addEntities(ball);

        ball.setLinearVelocity(5,-10 );*/

    }

    private void initBrick() {

        BoundingShape Box = box(92, 42);

        for (int i = 0; i < 12; i++) {

            GameEntity brick = new GameEntity();
            brick.getTypeComponent().setValue(Type.BRICK);
            brick.getPositionComponent().setValue((i % 11) *100 + 95, (i / 16)* 45 + 30);
            //brick.getPositionComponent().setValue(getWidth() / 2, 100);
            brick.getMainViewComponent().setTexture("Bricks/brick_blue_small.png", true);
            brick.getBoundingBoxComponent().addHitBox(new HitBox("BrickHitBox", Box));
            brick.addComponent(new CollidableComponent(true));


            getGameWorld().addEntities(brick);

            /*PhysicsEntity brick = new PhysicsEntity(Type.BRICK);
            brick.setPosition((i % 11) *100 + 95, (i / 16)* 45 + 30);
            brick.setGraphics(getAssets().getTexture("Bricks/brick_blue_small.png"));
            brick.setCollidable(true);

            addEntities(brick);*/
        }
    }

    private void initScreenBounds() {
        Entity walls = Entities.makeScreenBounds(150);
        walls.addComponent(new TypeComponent(Type.WALL));
        walls.addComponent(new CollidableComponent(true));

        getGameWorld().addEntity(walls);
    }
    @Override
    protected void onUpdate(double tpf) {


        //ballPhysics = new PhysicsComponent();
        /*batPhysics.setLinearVelocity(0,0);

        //Geschwindigkeit des Balls regeln. Wenn Geschw. unter 5 -> auf 5 setzen
        Point2D v = ballPhysics.getLinearVelocity();
        if(Math.abs(v.getY())<5) {
            double x = v.getX();
            double signY = Math.signum(v.getY());
            ballPhysics.setLinearVelocity(x, signY * 5);
        }*/
        /*bat.setLinearVelocity(0,0);

        //Geschwindigkeit des Balls regeln. Wenn Geschw. unter 5 -> auf 5 setzen
        Point2D v = ball.getLinearVelocity();
        if(Math.abs(v.getY())<5){
            double x = v.getX();
            double signY = Math.signum(v.getY());
            ball.setLinearVelocity(x, signY * 5 );
        }*/
    }



    public static void main(java.lang.String[] args) {
        launch(args);
    }

}
