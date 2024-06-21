package task2;

import task2.models2.Channel;
import task2.models2.Task;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
class Simulation implements Callable<double[]> {

    private final int channelAmount;
    private final int queueCapacity;
    private final int simTime;

    public Simulation(int channelAmount, int queueCapacity, int simTime) {
        this.channelAmount = channelAmount;
        this.queueCapacity = queueCapacity;
        this.simTime = simTime;
    }
    @Override
    public double[] call() {
        final AtomicInteger tasksGenerated = new AtomicInteger();
        final AtomicInteger tasksDenied = new AtomicInteger();
        final BlockingQueue<Task> queue = new ArrayBlockingQueue<>(queueCapacity);
        final ExecutorService channelPool = Executors.newFixedThreadPool(channelAmount);
        for (int i = 0; i < channelAmount; i++) {
            channelPool.submit(new Channel(queue));
        }

        final long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < simTime) {
            final Task request = new Task(tasksGenerated.incrementAndGet());
            if (!queue.offer(request)) {
                tasksDenied.incrementAndGet();
            }

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                break;
            }
        }
        channelPool.shutdownNow();
        final double avgQueueLength = queueCapacity - ((double) queue.remainingCapacity() / queueCapacity);
        final double denialProbability = (double) tasksDenied.get() / tasksGenerated.get();
        return new double[]{avgQueueLength, denialProbability};
    }
}
