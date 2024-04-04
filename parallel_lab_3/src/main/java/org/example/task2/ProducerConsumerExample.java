package org.example.task2;

public class ProducerConsumerExample {
    private static final int SIZE = 100;
    private static final Integer TERMINATOR = -1;
    private static final boolean ISRANDOM = false;

    public static void main(String[] args) {
        final Drop<Integer> drop = new Drop<>();

        (new Thread(new Producer(drop, SIZE, TERMINATOR, ISRANDOM))).start();
        (new Thread(new Consumer(drop, TERMINATOR, ISRANDOM))).start();
    }
}