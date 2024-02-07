package org.example.task3;

public class BallThread extends Thread{
    private Ball b;

    public BallThread(Ball ball,int priority){
        b =ball;
        this.setPriority(priority);
    }

    @Override
    public void run(){
        try {
            for (int i = 0; i < 100000000; i++) {
                b.move();
                System.out.println("thread name = "+
                        Thread.currentThread().getName());
                Thread.sleep(5);
            }
        } catch (InterruptedException ex){

        }
    }

}
