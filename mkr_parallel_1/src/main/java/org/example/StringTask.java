package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringTask {
    private static final int N = 5; // Кількість потоків
    private static final int ITERATIONS = 1000; // виконують 1000 ітерацій
    private static List<String> sharedList = new ArrayList<>(); // спільну для всіх потоків колекцією об'єктів  String.

    public static void main(String[] args) {
        Thread[] threads = new Thread[N];

        for (int i = 0; i < N; i++) {
            threads[i] = new Thread(() -> {
                Random random = new Random();
                for (int j = 0; j < ITERATIONS; j++) {
                    String randomString = generateRandomString(random, 10); // Генерація рядка з 10 випадкових символів
                    synchronized (sharedList) {
                        sharedList.add(randomString); // Додавання рядка
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Total strings added to shared list: " + sharedList.size());
    }

    private static String generateRandomString(Random random, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a'); // Випадковий символ від 'a' до 'z'
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
