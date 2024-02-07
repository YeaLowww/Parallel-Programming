package org.example.task4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 450;
    private static final int BLUE_PRIORITY = 1;
    private static final int RED_PRIORITY = 10;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.canvas = new BallCanvas();
        System.out.println("In Frame Thread name = " +
                Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");
        JButton buttonJoin = new JButton("Join");

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ball blue_b = new Ball(canvas);
                blue_b.setColor(Color.BLUE);
                canvas.add(blue_b);

                BallThread blue_thread = new BallThread(blue_b);
                blue_thread.start();
                System.out.println("Thread name = " +
                        Thread.currentThread().getName());
            }
        });

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.stopBalls();
                Ball b = new Ball(canvas);
                b.setColor(Color.RED);
                canvas.add(b);

                BallThread thread = new BallThread(b);
                thread.start();
                Thread a =new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            thread.join();
                        }catch (InterruptedException ex){
                            ex.printStackTrace();
                        }
                        canvas.runBalls();
                    }
                });
                a.start();
            }
        });


        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);
        buttonPanel.add(buttonJoin);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}
