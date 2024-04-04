package org.example.task2;

public class Consumer implements Runnable {
    private final Object terminator;
    private final Drop<?> drop;
    private final boolean isRandom;

    public Consumer(Drop<?> drop, Object terminator, boolean isRandom) {
        this.drop = drop;
        this.terminator = terminator;
        this.isRandom = isRandom;
    }

    public void run() {
        for (Object message = drop.take(); !message.equals(terminator); message = drop.take()) {
            if (isRandom) System.out.format("Received message: %s%n", message);
            else System.out.format("Received message #%s%n", message);
        }
    }
}