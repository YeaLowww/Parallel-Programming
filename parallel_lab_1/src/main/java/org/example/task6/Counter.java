package org.example.task6;

public class Counter {
    private int value = 0;
    private final Object lock = new Object();
    public synchronized void inc() {
        value++;
    }

    public synchronized void dec() {
        value--;
    }
    public void incSyncBlock() {
        synchronized (lock) { value++; }
    }
    public void decSyncBlock() {
        synchronized (lock) { value--; }
    }
    public synchronized int getValue() {
        return value;
    }
    public void unsyncInc() {
        value++;
    }
    public void unsyncDec() {
        value--;
    }
}
