package Breakout;/**
 * Created by Romano on 01.07.2016.
 */

import com.almasb.fxgl.GameApplication;
import com.almasb.fxgl.GameSettings;
import com.almasb.fxgl.asset.AssetManager;
import com.almasb.fxgl.asset.Assets;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityType;
import com.almasb.fxgl.physics.PhysicsEntity;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.jbox2d.dynamics.BodyType;

import static javafx.application.Application.launch;

public class Breakout extends GameApplication{

    private Assets assets;

    private PhysicsEntity bat, ball;

    private enum Type implements EntityType{
        BAT, BALL, BRICK
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
        inputManager.addKeyPressBinding(KeyCode.A, () ->{
            bat.setLinearVelocity(-5,0);
        });

        inputManager.addKeyPressBinding(KeyCode.D, () -> {
            bat.setLinearVelocity(5, 0);
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
        initBat();
        initBall();
        initBrick() ;

    }



    @Override
    protected void initUI(Pane uiRoot) {
        Text scoreText = new Text();
        scoreText.setTranslateY(20);
        scoreText.setTranslateX(5);
        scoreText.setFont(Font.font(20));
        scoreText.setText("PUNKTE:");

        uiRoot.getChildren().add(scoreText);
    }

    private void initBat() {
        bat = new PhysicsEntity(Type.BAT);
        bat.setPosition(getWidth()/2 - 135 / 2, getHeight()- 32);
        bat.setGraphics(assets.getTexture("Bats/bat_black.png"));
        bat.setBodyType(BodyType.KINEMATIC);

        addEntities(bat);

    }

    private void initBall() {
        ball = new PhysicsEntity(Type.BALL);
        ball.setPosition(getWidth()/2 - 35 / 2, getHeight()/2 - 35 / 2);
        ball.setGraphics(assets.getTexture("Balls/ball_red.png"));
        ball.setBodyType(BodyType.DYNAMIC);

        addEntities(ball);

        ball.setLinearVelocity(5,-10 );

    }

    private void initBrick(){
        for (int i = 0; i < 60; i++){
            PhysicsEntity brick = new PhysicsEntity(Type.BRICK);
            brick.setPosition((i % 12) *98, (i / 16)* 45);
            brick.setGraphics(assets.getTexture("Bricks/brick_blue_small.png"));


            addEntities(brick);
        }
    }

    @Override
    protected void onUpdate() {
        bat.setLinearVelocity(0,0);
    }
    public static void main(String[] args) {
        launch(args);
    }

}
