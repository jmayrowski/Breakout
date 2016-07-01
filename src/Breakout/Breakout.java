package Breakout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Romano on 30.06.2016.
 */
public class Breakout extends JPanel {

    public int width;
    public int height;
    private int tickrate = 60;
    private Ball ball;
    public Player player;
    private boolean isRunning = false;
    private boolean isPaused = false;

    private long lastUpdate;

    public  Breakout(int width, int height){
        this.width = width;
        this.height = height;

        ball = new Ball(this);
        player = new Player(this);
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !isRunning) run();
                //if (e.getKeyCode() == KeyEvent.VK_ESCAPE) pause();
                if (e.getKeyCode() == KeyEvent.VK_Q ) quit();
                if (e.getKeyCode() == KeyEvent.VK_LEFT) player.position.x = player.position.x - 50;
                if (e.getKeyCode() == KeyEvent.VK_RIGHT)player.position.x =player.position.x + 50;
            }
        });
    }

    public void run(){
        thread.start();
    }

    public void pause(){
        isPaused = !isPaused;
    }

    public void quit(){
        isRunning = false;
    }

    Thread thread = new Thread(){
        public void run(){
            //Init

            isRunning = true;
            isPaused = false;
            lastUpdate = System.nanoTime();
            //Loop
            while (isRunning){
                try {

                    if (isPaused) {
                        lastUpdate = System.nanoTime();
                        Thread.sleep(1 );

                    } else {
                        tick();
                        lastUpdate = System.nanoTime();
                        Thread.sleep((long)(1000.0/tickrate));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            //Exit

        }
    };

    public void tick(){
        double deltatime = (System.nanoTime()-lastUpdate)/1000000 ; //in ms
        ball.tick(deltatime);

        repaint();
    }

    public void paint (Graphics g){


        g.translate((getWidth()-width)/2, (getHeight()-height)/2); //Game wird gecentert...optional
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.WHITE);

        if (!isRunning) {
            g.drawString("Drücke Leertaste, um das Spiel zu starten.", 200, 200);
        }else if(isPaused){
            g.drawString("Spiel ist pausiert", 100, 100);
        }

        g.translate(width/2, height/2);
        ball.render(g);
        player.render(g);
    }
}
