package task3.models3;

import java.util.concurrent.BlockingQueue;

public class Channel implements Runnable {
    private final BlockingQueue<Task> queue;

    public Channel(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Task task = queue.take();
                System.out.println("Завдання #" + task.getId() + " оброблюється в каналі " + Thread.currentThread().getName());
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
