package org.example.task4;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class Main {
    public static void main(String[] args) throws IOException {
        File[] files = new File("C:\\Users\\Саша Головня\\IdeaProjects\\parallel_lab_4\\src\\main\\java\\org\\example\\data\\").listFiles();
        assert files != null;
        final String[] keywords = {"lorem" };

        for (File file : files) {
            final String text = Files.readString(file.toPath());
            if (KeywordMatcher.matchesKeywords(keywords, text)) {
                System.out.println("Found matches in file: " + file.getPath());
            }
        }
    }
}
