package org.example.task4;

public class BallThread extends Thread {
    private Ball b;

    public BallThread(Ball ball) {
        b = ball;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 1000; i++) {
                if (b.isStopped()) {
                    while (b.isStopped()) {
                        Thread.sleep(0);
                    }
                }
                b.move();
                System.out.println("thread name = " +
                        Thread.currentThread().getName());
                Thread.sleep(5);
            }
        } catch (InterruptedException ex) {

        }
    }

}
