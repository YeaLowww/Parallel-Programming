package org.example.task1;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Ball {
    private Component canvas;
    private static final int xSIZE = 20;
    private static final int ySIZE = 20;

    private int x = 0;
    private int y = 0;
    private int dx = 2;
    private int dy = 2;

    public Ball(Component c) {
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
        g2.setColor(Color.blue);
        g2.fill(new Ellipse2D.Double(x, y, xSIZE, ySIZE));
    }

    public void move() {
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

}
