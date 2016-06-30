package Breakout;

import java.awt.*;
import javax.swing.*;

import static java.awt.BorderLayout.CENTER;

public class Main{

    public static void Main(String[] args) {
        JFrame frame = new JFrame("BREAKOUT");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(1280, 720);

        Breakout game = new Breakout() ;


        frame.setVisible(true);
}


}