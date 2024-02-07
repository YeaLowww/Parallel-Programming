package org.example.task5;

public class TheBetterTheadedPrint extends Thread{
    private final String symbol;
    public TheBetterTheadedPrint(String symbol) {
        this.symbol = symbol;
    }
    private static final Object lock = new Object();

    public void run() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                synchronized (lock) {
                    lock.notify();
                    System.out.print(this.symbol);
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println();
        }
        System.exit(0);
    }
}
