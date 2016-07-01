package Breakout;

import java.awt.*;
import javax.swing.*;

public class Main{

    public static void main(String[] args) {

        // neues Fenster erstellen
        JFrame frame = new JFrame("BREAKOUT");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(1280, 720); // Fenstergröße = HD
        frame.setLocationRelativeTo(null);

        Breakout game = new Breakout(1280, 720) ;

        frame.add(game, BorderLayout.CENTER);
        frame.setVisible(true);
}


}