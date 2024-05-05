package org.example.task3;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class CommonWordsFinder {

    private static class CommonWordsTask extends RecursiveTask<Set<String>> {
        private final List<String> words1;
        private final List<String> words2;

        public CommonWordsTask(List<String> words1, List<String> words2) {
            this.words1 = words1;
            this.words2 = words2;
        }

        @Override
        protected Set<String> compute() {
            Set<String> commonWords = new HashSet<>();
            for (String word : words1) {
                if (words2.contains(word)) {
                    commonWords.add(word);
                }
            }
            return commonWords;
        }
    }

    public static Set<String> findCommonWords(String filePath1, String filePath2) throws Exception {
        String content1 = new String(Files.readAllBytes(Paths.get(filePath1)));
        String content2 = new String(Files.readAllBytes(Paths.get(filePath2)));

        List<String> words1 = Arrays.asList(content1.split("\\s+"));
        List<String> words2 = Arrays.asList(content2.split("\\s+"));

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new CommonWordsTask(words1, words2));
    }


    public static void main(String[] args) throws Exception {
        String filePath1 = "C:\\Users\\Саша Головня\\IdeaProjects\\parallel_lab_4\\src\\main\\java\\org\\example\\data\\lorem.txt";
        String filePath2 = "C:\\Users\\Саша Головня\\IdeaProjects\\parallel_lab_4\\src\\main\\java\\org\\example\\data\\test.txt";
        Set<String> commonWords = findCommonWords(filePath1, filePath2);
        System.out.println("Common words: " + commonWords);
    }
}
