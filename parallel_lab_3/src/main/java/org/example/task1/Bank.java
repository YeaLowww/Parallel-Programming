package org.example.task1;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    public static final int NTEST = 10000;

    private final int[] accounts;
    private final AtomicLong ntransacts = new AtomicLong(0);

    public Bank(int len, int initBalance) {
        accounts = new int[len];
        Arrays.fill(accounts, initBalance);
    }

    //    variant 1
    public synchronized void transferV1(int from, int to, int amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts.incrementAndGet();
        if (ntransacts.get() % NTEST == 0) {
            test();
            Thread.currentThread().interrupt();
        }
    }

    //    variant 2
    public void transferV2(int from, int to, int amount) {
        synchronized (accounts) {
            accounts[from] -= amount;
            accounts[to] += amount;
            if (ntransacts.incrementAndGet() % NTEST == 0) {
                test();
                Thread.currentThread().interrupt();
            }
        }
    }

    //    variant 3
    private final Lock lock = new ReentrantLock();
    public void transferV3(int from, int to, int amount) {
        try {
            lock.lock();
            accounts[from] -= amount;
            accounts[to] += amount;
            if (ntransacts.incrementAndGet() % NTEST == 0) {
                test();
                Thread.currentThread().interrupt();
            }
        } finally {
            lock.unlock();
        }
    }

    public void test() {
        int sum = 0;
        for (int account : accounts) {
            sum += account;

        }
        System.out.println("Transactions: " + ntransacts + " | Sum: " + sum);
    }

    public int size() {
        return accounts.length;
    }
}