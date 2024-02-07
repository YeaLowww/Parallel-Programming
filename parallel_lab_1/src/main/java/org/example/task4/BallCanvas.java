package org.example.task4;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class BallCanvas extends JPanel {
    private ArrayList<Ball> balls = new ArrayList<>();
    public void add(Ball b){
        this.balls.add(b);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (Ball b:balls) {
            b.draw(g2);
        }
    }
    public void stopBalls() {
        for (Ball ball : balls) {
            ball.stopBall();
        }
    }
    public void runBalls() {
        for (Ball ball : balls) {
            ball.runBall();
        }
    }
}
