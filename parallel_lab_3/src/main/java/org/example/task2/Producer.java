package org.example.task2;

import java.util.Random;

public class Producer implements Runnable {
    private final int size;
    private final Drop<Integer> drop;
    private final Integer terminator;
    private final boolean isRandom;
    private final Random random = new Random();

    public Producer(Drop<Integer> drop, int size, Integer terminator, boolean isRandom) {
        this.drop = drop;
        this.size = size;
        this.terminator = terminator;
        this.isRandom = isRandom;
    }

    public void run() {
        for (int i = 0; i < size; i++) {
            if (isRandom) drop.put(random.nextInt(Integer.MAX_VALUE));
            else drop.put(i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {}
        }
        drop.put(terminator);
    }

}
