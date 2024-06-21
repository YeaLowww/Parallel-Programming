package task1;

import task1.models1.Channel;
import task1.models1.Task;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        final int channelAmount = 15;
        final int queueCapacity = 10;
//        in ms
        final int simulationTime = 10000;
        final int taskCooldown = 40;

        final Random random = new Random();
        final AtomicInteger tasksGenerated = new AtomicInteger();
        final AtomicInteger tasksDenied = new AtomicInteger();
        final BlockingQueue<Task> queue = new ArrayBlockingQueue<>(queueCapacity);
        final ExecutorService channelPool = Executors.newFixedThreadPool(channelAmount);

        for (int i = 0; i < channelAmount; i++) {
            channelPool.submit(new Channel(queue));
        }

        final long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < simulationTime) {
            final int execTime = random.nextInt(1000) + 500;
            final Task task = new Task(tasksGenerated.incrementAndGet(), execTime);


            if (!queue.offer(task)) {
                tasksDenied.incrementAndGet();
            }

            try {
                Thread.sleep(taskCooldown);
            } catch (InterruptedException e) {
                break;
            }
        }


        channelPool.shutdownNow();
        channelPool.shutdownNow();

        final double avgQueueLength = queueCapacity - ((double) queue.remainingCapacity() / queueCapacity);
        final int denialProbability = 100 * tasksDenied.get() / tasksGenerated.get();

        System.out.println(String.format(
                "------------------------------------ \n" +
                        "Кількість каналів: %d \n" +
                        "Затримка між створенням завдань: %dмс \n" +
                        "Загальна довжина черги: %d \n" +
                        "Кількість створених завдань: %d \n" +
                        "------------------------------------ \n" +
                        "Середня довжина черги: %.2f \n" +
                        "Ймовірність відмови: %d%%",
                channelAmount, taskCooldown, queueCapacity, tasksGenerated.get(),
                avgQueueLength, denialProbability
        ));
    }
}