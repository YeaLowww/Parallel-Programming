package org.example.task2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class BallCanvas extends JPanel {
    private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<Pocket> pockets;
    private int inPocketCounter=0;
    JLabel inPocketCounterLabel;
    public void add(Ball b){
        this.balls.add(b);
    }
    public ArrayList<Pocket> getPockets() {
        return pockets;
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (Ball ball : balls) {
            if (ball.isInPocket()) {
                inPocketCounter++;
            }
        }
        pockets = generatePockets();
        paintPockets(g2);
        inPocketCounterLabel.setText("Amount in pocket: " + inPocketCounter);
        balls.removeIf(ball -> ball.isInPocket());

        for (Ball b:balls) {
            b.draw(g2);
        }
    }
    private  ArrayList<Pocket> generatePockets(){
        ArrayList<Pocket> pockets = new ArrayList<>();
        pockets.add(new Pocket(0, 0, 20));
        pockets.add(new Pocket(0, this.getHeight() - 40, 20));
        pockets.add(new Pocket(this.getWidth() - 40, 0, 20));
        pockets.add(new Pocket(this.getWidth() - 40, this.getHeight() - 40, 20));

        return pockets;
    }
    private void paintPockets(Graphics2D g2){
        for (Pocket p: pockets) {
            p.draw(g2);
        }
    }

}
