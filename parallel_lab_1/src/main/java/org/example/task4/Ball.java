package org.example.task4;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;

public class Ball {
    private Component canvas;
    private static final int xSIZE = 20;
    private static final int ySIZE = 20;

    private int x = 0;
    private int y = 0;
    private int dx = 2;
    private int dy = 2;
    private Color color;
    private boolean isStopped = false;
    public void stopBall() {
        isStopped = true;
    }

    public void runBall() {
        isStopped = false;
    }
    public Ball( Component c) {
        this.canvas = c;
        if (Math.random() < 0.5) {
            x = new Random().nextInt(this.canvas.getWidth());
            y = 0;
        } else {
            x = 0;
            y = new Random().nextInt(this.canvas.getHeight());
        }
    }

    public void f() {
        int a = 0;
    }

    public void draw(Graphics2D g2) {
        if (color== Color.BLUE){g2.setColor(Color.blue);}
        if (color== Color.RED){g2.setColor(Color.red);}
        g2.fill(new Ellipse2D.Double(x, y, xSIZE, ySIZE));
    }

    public void move() {
        if(isStopped) return;
        x += dx;
        y += dy;

        if (x < 0) {
            x = 0;
            dx = -dx;
        }
        if (x + xSIZE >= this.canvas.getWidth()) {
            x = this.canvas.getWidth() - xSIZE;
            dx = -dx;
        }
        if (y < 0) {
            y = 0;
            dy = -dy;
        }
        if (y + ySIZE >= this.canvas.getHeight()) {
            y = this.canvas.getHeight() - ySIZE;
            dy = -dy;
        }
        this.canvas.repaint();
    }
    public boolean isStopped() {
        return isStopped;
    }

    public void setStopped(boolean isStopped) {
        this.isStopped = isStopped;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
