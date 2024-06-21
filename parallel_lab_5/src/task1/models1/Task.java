package task1.models1;

public class Task {
    private final int id;
    //    in ms
    private final int processingTime;

    public Task(int id, int processingTime) {
        this.id = id;
        this.processingTime = processingTime;
    }

    public void startProcess() {
        try {
            System.out.println(id + " | Буде оброблятись протягом " + processingTime + "мс");
            Thread.sleep(processingTime);
            System.out.println(id + " | Успішно оброблено");
        } catch (InterruptedException e) {
            System.out.println(id + " | Прервано обробку");
        }
    }

}