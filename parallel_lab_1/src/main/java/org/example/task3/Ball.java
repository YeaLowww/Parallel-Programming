package org.example.task3;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ball {
    private Component canvas;
    private static final int xSIZE = 20;
    private static final int ySIZE = 20;

    private int x = 0;
    private int y = 0;
    private int dx = 2;
    private int dy = 2;
    private Color color;
    public Ball( Component c) {
        this.canvas = c;
        x = this.canvas.getWidth()/2;
        y = this.canvas.getHeight()/2;
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
    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
