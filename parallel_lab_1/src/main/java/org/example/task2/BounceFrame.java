package org.example.task2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 450;
    public  JLabel inPocketCountLabel;

    public BounceFrame(){
        this.setSize(WIDTH, HEIGHT);
        this.canvas = new BallCanvas();
        System.out.println("In Frame Thread name = "+
                Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas,BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");

        inPocketCountLabel = new JLabel("Amount in pocket: 0");
        canvas.inPocketCounterLabel = inPocketCountLabel;

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ball b = new Ball(canvas.getPockets(),canvas);
                canvas.add(b);

                BallThread thread = new BallThread(b);
                thread.start();
                System.out.println("Thread name = "+
                        Thread.currentThread().getName());

            }
        });
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);
        buttonPanel.add(inPocketCountLabel);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}
