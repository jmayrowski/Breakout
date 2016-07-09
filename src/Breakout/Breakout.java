package Breakout;/**
 * Created by Romano on 01.07.2016.
 */


import Breakout.Control.BatControl;
import com.almasb.ents.Entity;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.entity.component.TypeComponent;
import com.almasb.fxgl.input.ActionType;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.InputMapping;
import com.almasb.fxgl.input.OnUserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.texture.Texture;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;


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

    private PhysicsComponent batPhysics;
    private PhysicsComponent ballPhysics;

    private Texture batTexture;
    private Texture ballTexture;
    private Texture brickTexture;
    private Texture wallTexture;

    private Text scoreText;

    private BatControl batControl;

    private ArrayList<GameEntity> playField;

    private IntegerProperty score = new SimpleIntegerProperty();
    public enum Type {
        BAT, BALL, BRICK, WALL, GROUND
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Breakout");
        settings.setVersion("dev");
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setIntroEnabled(false);
        settings.setApplicationMode(ApplicationMode.DEBUG);
    }

    @Override
    protected void initInput() {

        Input input = getInput();

        input.addInputMapping(new InputMapping("left", KeyCode.A));

        input.addInputMapping(new InputMapping("right", KeyCode.D));

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


    @Override
    protected void initAssets() {


        ballTexture = getAssetLoader().loadTexture("Balls/ball_red.png");
        batTexture = getAssetLoader().loadTexture("Bats/bat_black.png");
        brickTexture = getAssetLoader().loadTexture("Bricks/brick_blue_small.png");
        wallTexture = getAssetLoader().loadTexture("Walls/brick_red.png");

    }

    @Override
    protected void initGame() {

        initWalls();
        initBat();
        initBall();
        initBrick();
        initBackground();
        //initScreenBounds();



    }

    @Override
    protected void initPhysics() {
        //Kollisionsabfrage zw. Ball und Brick
        PhysicsWorld physics = getPhysicsWorld();

        physics.setGravity(0,1);
        physics.addCollisionHandler(new CollisionHandler(Type.BALL, Type.BRICK) {
            @Override
            public void onCollisionBegin(Entity a, Entity b) {

                /**Hier wird bei der Kollisionsabfrage mit einem Ziegel die Richtung des Balls umgedreht
                Das klappt weitestgehend auch, jedoch wird diese Prozedur oftmals doppelt durchgeführt,
                weshalb der Ball dann einfach weiter fliegt**/

                Point2D v = BallFactory.ballPhysics.getLinearVelocity();
                double x = - v.getX();
                double y = - v.getY();
                BallFactory.ballPhysics.setLinearVelocity(x,y);

            }
            @Override
            public void onCollision(Entity a, Entity b) {


            }
            @Override
            public void onCollisionEnd(Entity a, Entity b) {
                b.removeFromWorld();
                score.set(score.get() + 100);
            }
        });

        physics.addCollisionHandler(new CollisionHandler(Type.BALL, Type.GROUND) {
            @Override
            public void onCollisionBegin(Entity a, Entity b) {
                //Was passiert wenn der Ball den Boden berührt?
                /**Diese Prozedur wird nur sporadisch ausgeführt. Das hängt damit zusammen,
                 * dass die Wände keine gescheite HitBox haben, zumindest prahlt der Ball nicht ab.
                 * Daher musste ich extra eine Bildschirmbegrenzung anfügen.
                 **/

                score.set(score.get() - 1000);
                a.removeFromWorld();
                initBall();
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

    private void initBackground() {}

    private void initWalls() {


        WallFactory wall = new WallFactory();
        int i;

        for (i = 0; i < 60; i++) {

            Entity top =  wall.createWalls("top", getWidth(), getHeight(), i);
            getGameWorld().addEntities(top);

        }


        for (i = 0; i < 60; i++) {

            Entity left = wall.createWalls("left", getWidth(), getHeight(), i);
            getGameWorld().addEntities(left);
        }

        for (i = 0; i < 60; i++) {

            Entity right = wall.createWalls("right", getWidth(), getHeight(), i);
            getGameWorld().addEntities(right);
        }

        Entity bot = wall.createWalls("bot", getWidth(), getHeight(), i = 0);
        getGameWorld().addEntities(bot);
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

        BatFactory bf = new BatFactory();
        Entity bat = bf.createBat(getWidth() / 2 - 135 / 2, getHeight() - 32);
        getGameWorld().addEntities(bat);

        batControl = bat.getControlUnsafe(BatControl.class );
    }

    private void initBall() {

        BallFactory bf = new BallFactory();
        getGameWorld().addEntities(bf.createBall( getWidth()/2 - 35 / 2, getHeight()/2 - 35 / 2));
        //ballPhysics = bf.ballPhysics;
    }

    private void initBrick() {

        PlayField pf = new PlayField();

        playField = new ArrayList<GameEntity>();
        playField = pf.getPlayField();

        for(int i = 0; i < 12; i++) {

            getGameWorld().addEntities(playField.get(i));
        }

        playField.clear();
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
