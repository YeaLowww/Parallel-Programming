package task2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final int simCount = 10;
        final int channelCount = 5;
        final int queueCapacity = 10;
        final int simTime = 10000;

        final ExecutorService simulationPool = Executors.newFixedThreadPool(simCount);
        final List<Future<double[]>> results = new ArrayList<>();

        for (int i = 0; i < simCount; i++) {
            results.add(simulationPool.submit(new Simulation(channelCount, queueCapacity, simTime)));
        }
        simulationPool.shutdown();
        simulationPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        double totalAvgQueueLength = 0;
        double totalDenialProbability = 0;

        for (Future<double[]> result : results) {
            final double[] values = result.get();
            totalAvgQueueLength += values[0];
            totalDenialProbability += values[1];
        }

        final double avgQueueLength = totalAvgQueueLength / simCount;
        final int denialProbability = (int) (100 * totalDenialProbability / simCount);

        System.out.println(String.format(
                "------------------------------------------------------\n" +
                        "Середня довжина черги: %.2f \n" +
                        "Ймовірність відмови: %d%%",
                avgQueueLength, denialProbability));
    }
}