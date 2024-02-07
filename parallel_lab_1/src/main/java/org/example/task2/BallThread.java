package org.example.task2;

public class BallThread extends Thread{
    private Ball b;

    public BallThread(Ball ball){
        b =ball;
    }

    @Override
    public void run(){
        try {
            for (int i = 0; i < 100000000; i++) {
                if (b.isInPocket()) {
                    this.interrupt();
                    return;
                }
                b.move();
                System.out.println("thread name = "+
                        Thread.currentThread().getName());
                Thread.sleep(5);
            }
        } catch (InterruptedException ex){

        }
    }

}
