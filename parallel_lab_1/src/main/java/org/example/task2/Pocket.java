package org.example.task2;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Pocket {
    private final int x;
    private final int y;
    private int radius;

    public int getX(){return x;}
    public int getY(){return y;}
    public int getRadius(){return radius;}
    public Pocket(int x,int y,int radius){
        this.x=x;
        this.y=y;
        this.radius=radius;
    }
    public void draw(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.fill(new Ellipse2D.Double(x,y,radius*2,radius*2));
    }
    public boolean isBallInPocket(int x,int y){
        double pocketRadius = this.getRadius()*2;
        double distance = Math.sqrt(Math.pow(this.getX()-x,2)+Math.pow(this.getY()-y,2));
        return distance<=pocketRadius;
    }
}
