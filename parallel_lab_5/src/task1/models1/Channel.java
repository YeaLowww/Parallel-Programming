package task1.models1;

import java.util.concurrent.*;

public class Channel implements Runnable {
    private final BlockingQueue<Task> queue;

    public Channel(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                final Task task = queue.take();
                task.startProcess();
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
