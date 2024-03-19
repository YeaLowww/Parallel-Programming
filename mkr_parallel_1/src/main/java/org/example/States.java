package org.example;

public class States {
    private static Object lock = new Object();
    private static String state = "r";

    public static void main(String[] args) {
        Thread A = new Thread(() -> { //A
            while (true) {
                synchronized (lock) {
                    if (state.equals("r")) {state = "w";} else {state = "r";}
                    lock.notifyAll();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread B = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (!state.equals("r")) {
                        try {
                            long countdown = 100;
                            while (countdown > 0) {
                                System.out.println("зворотний відлік: " + countdown);
                                countdown--;
                                Thread.sleep(1);
                            }
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        A.start();
        B.start();
    }
}