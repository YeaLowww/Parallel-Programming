package task3;

import java.util.concurrent.atomic.AtomicBoolean;

class StatsDisplay implements Runnable {
    private final Simulation simulation;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public StatsDisplay(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public void run() {
        while (running.get()) {
            printStats();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
        printStats();
    }

    private void printStats() {
        final double[] simOutput = simulation.getOutputValues();
        System.out.println(String.format(
                "------------------------------------------------------\n" +
                        "%s \n" +
                        "Середня довжина черги: %.2f \n" +
                        "Ймовірність відмови: %d%% \n" +
                        "------------------------------------------------------",
                simulation.getState(), simOutput[0], (int) (simOutput[1] * 100)
        ));
    }

    public void stop() {
        running.set(false);
    }
}
