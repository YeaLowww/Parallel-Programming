package org.example;
import java.util.concurrent.ArrayBlockingQueue;

public class ABC {
    public static void main(String[] args) {
        ArrayBlockingQueue<Object> buffer = new ArrayBlockingQueue<>(3); // буфер

        Thread A = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    Object obj = new Object();
                    buffer.put(obj);// створення та додавання об'єктів в буфер
                    System.out.println("Thread A added: " + obj);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread B = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    Object obj = new Object();
                    buffer.put(obj);// створення та додавання об'єктів в буфер
                    System.out.println("Thread B added: " + obj);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread C = new Thread(() -> {
            try {
                while (true) {
                    Object obj = buffer.take();
                    System.out.println("Thread C remove: " + obj);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        A.start();
        B.start();
        C.start();
    }
}