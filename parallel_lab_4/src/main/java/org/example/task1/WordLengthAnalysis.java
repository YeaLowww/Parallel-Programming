package org.example.task1;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class WordLengthAnalysis extends RecursiveTask<WordStats> {
    private static final int THRESHOLD = 10; // Поріг для розділення обробки

    private final String[] words;
    private final int start;
    private final int end;
    private final int threadCount;

    public WordLengthAnalysis(String[] words, int start, int end, int threadCount) {
        this.words = words;
        this.start = start;
        this.end = end;
        this.threadCount = threadCount;
    }

    @Override
    protected WordStats compute() {
        if (end - start < THRESHOLD) {
            return computeDirectly();
        } else {
            int middle = start + (end - start) / 2;
            WordLengthAnalysis leftTask = new WordLengthAnalysis(words, start, middle, threadCount);
            WordLengthAnalysis rightTask = new WordLengthAnalysis(words, middle, end, threadCount);

            leftTask.fork();
            WordStats rightResult = rightTask.compute();
            WordStats leftResult = leftTask.join();

            return leftResult.merge(rightResult);
        }
    }

    private WordStats computeDirectly() {
        int totalLength = 0;
        int totalCount = 0;

        for (int i = start; i < end; i++) {
            totalLength += words[i].length();
            totalCount++;
        }

        return new WordStats(totalLength, totalCount);
    }

    public static WordStats analyze(String[] words, int threadCount) {
        ForkJoinPool pool = new ForkJoinPool(threadCount);
        return pool.invoke(new WordLengthAnalysis(words, 0, words.length, threadCount));
    }

    public static void main(String[] args) {
        String text = "Sample text for analysis. This is a test.";
        String[] words = text.split("\\s+");
        WordStats stats = WordLengthAnalysis.analyze(words, 4); // Приклад з використанням 4 потоків
        System.out.println("Average word length: " + (double) stats.getTotalLength() / stats.getTotalCount());
    }
}




