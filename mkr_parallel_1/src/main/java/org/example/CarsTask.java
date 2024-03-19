package org.example;
import java.util.concurrent.TimeUnit;

public class CarsTask {
    private static volatile boolean trafficLightGreen = false;
    private static final Object lock = new Object();
    private static int carsPassed = 0;

    public static void main(String[] args) {
        TrafficLight trafficLight = new TrafficLight();
        Thread trafficLightThread = new Thread(trafficLight);
        trafficLightThread.start();

        for (int i = 0; i < 100; i++) {
            Thread carThread = new Thread(new Car(i));
            carThread.start();
        }

        while (carsPassed < 10000) {
            try {
                TimeUnit.MILLISECONDS.sleep(1000); // Перевірка кожну секунду
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        trafficLight.stop(); // Зупиняємо роботу світлофору
        trafficLightThread.interrupt(); // Прериваємо потік світлофору
    }

    static class TrafficLight implements Runnable {
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                synchronized (lock) {
                    try {
                        setTrafficLight("green");
                        TimeUnit.MILLISECONDS.sleep(70);
                        setTrafficLight("yellow");
                        TimeUnit.MILLISECONDS.sleep(10);
                        setTrafficLight("red");
                        TimeUnit.MILLISECONDS.sleep(40);
                        setTrafficLight("yellow");
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void stop() {
            running = false;
        }
    }

    static class Car implements Runnable {
        private final int id;

        public Car(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(2); // Затримка для автівок
                    synchronized (lock) {
                        if (trafficLightGreen) {
                            System.out.println("Car " + id + " passed the traffic light.");
                            carsPassed++;
                            lock.notifyAll(); // Сповіщення інших потоків, що звільнилася машина
                            break; // Вихід з циклу, якщо машина проїхала
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private static void setTrafficLight(String color) {
        switch (color) {
            case "green":
                trafficLightGreen = true;
                break;
            case "yellow":
                trafficLightGreen = false;
                break;
            case "red":
                trafficLightGreen = false;
                break;
        }
        System.out.println("Traffic light is now " + color);
    }
}