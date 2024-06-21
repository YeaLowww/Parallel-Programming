package task3;
import task3.models3.Channel;
import task3.models3.Task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class Simulation implements Runnable {
    private final int numChannels;
    private final int queueCapacity;
    private final int simulationTime;
    private final AtomicInteger requestsGenerated = new AtomicInteger();
    private final AtomicInteger requestsDenied = new AtomicInteger();
    private final BlockingQueue<Task> queue;
    private final ExecutorService channelPool;

    public Simulation(int numChannels, int queueCapacity, int simulationTime) {
        this.numChannels = numChannels;
        this.queueCapacity = queueCapacity;
        this.simulationTime = simulationTime;
        this.queue = new ArrayBlockingQueue<>(queueCapacity);
        this.channelPool = Executors.newFixedThreadPool(numChannels);
    }

    @Override
    public void run() {
        for (int i = 0; i < numChannels; i++) {
            channelPool.submit(new Channel(queue));
        }

        final long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < simulationTime) {
            final Task task = new Task(requestsGenerated.incrementAndGet());
            if (!queue.offer(task)) {
                requestsDenied.incrementAndGet();
            }

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                break;
            }
        }

        channelPool.shutdownNow();
    }

    public String getState() {
        return String.format(
                "Запитів у черзі: %d\n" +
                        "Обслуговано запитів: %d",
                queue.size(),
                requestsGenerated.get() - requestsDenied.get() - queue.size());
    }

    public double[] getOutputValues() {
        final double avgQueueLength = queueCapacity - ((double) queue.remainingCapacity() / queueCapacity);
        final double denialProbability = (double) requestsDenied.get() / requestsGenerated.get();
        return new double[]{avgQueueLength, denialProbability};
    }
}
