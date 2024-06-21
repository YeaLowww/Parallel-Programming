package task3;

public class Main {
    public static void main(String[] args) {
        final int channelCount = 5;
        final int queueCapacity = 10;
        final int simTime = 10000;

        final Simulation simulation = new Simulation(channelCount, queueCapacity, simTime);
        final StatsDisplay display = new StatsDisplay(simulation);

        final Thread simulationThread = new Thread(simulation);
        final Thread displayThread = new Thread(display);
        simulationThread.start();
        displayThread.start();

        try {
            simulationThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        display.stop();
        try {
            displayThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}