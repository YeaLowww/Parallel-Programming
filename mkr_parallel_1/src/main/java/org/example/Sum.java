package org.example;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;
class SumTask implements Runnable {

    private static double totalSum = 0;
    private double[] data;

    public SumTask(double[] data) {
        this.data = data;
    }

    @Override
    public void run() {
        double localSum = 0;
        for (double d : data) {
            localSum += d;
        }
        addSum(localSum);
    }

    private synchronized void addSum(double sum) {
        totalSum += sum;
    }

    public static synchronized double getTotalSum() {
        return totalSum;
    }
}
public class Sum {

    public static void main(String[] args) {
        int numTasks = 100;
        double[] data = createArray(1000000); // створює масив дійсних чисел.

        ExecutorService pool = Executors.newFixedThreadPool(8); // Створення пулу 8 потоків.

        int startIndex = 0;
        int chunkSize = data.length / numTasks;
        for (int i = 0; i < numTasks; i++) {
            int endIndex = startIndex + chunkSize;
            if (i == numTasks - 1) {
                endIndex = data.length;
            }
            double[] chunk = getChunk(data, startIndex, endIndex); // повертає підмасив даних для поточної задачі.
            pool.submit(new SumTask(chunk)); // Створення та відправка задачі до пулу.
            startIndex = endIndex;
        }

        pool.shutdown(); // Завершення роботи пулу.

        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); // Очікування завершення всіх задач у пулі.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double totalSum = SumTask.getTotalSum(); // повертає загальну суму всіх задач.
        System.out.println("Total sum: " + totalSum);
    }

    private static double[] createArray(int size) {
        double[] data = new double[size];
        for (int i = 0; i < size; i++) {
            data[i] = Math.random();
        }
        return data;
    }

    private static double[] getChunk(double[] data, int startIndex, int endIndex) {
        return Arrays.copyOfRange(data, startIndex, endIndex);
    }
}


