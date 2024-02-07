package org.example.task5;

public class Main {
    public static void main(String[] args) {
//        ThreadedPrint thread1 = new ThreadedPrint("-");
//        ThreadedPrint thread2 = new ThreadedPrint("|");
//        thread1.start();
//        thread2.start();
        TheBetterTheadedPrint order_thread1 = new TheBetterTheadedPrint("-");
        TheBetterTheadedPrint order_thread2 = new TheBetterTheadedPrint("|");
        order_thread1.start();
        order_thread2.start();

    }
}
