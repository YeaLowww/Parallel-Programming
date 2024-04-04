package org.example.task3;

import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int WEEKS = 5;
        final int TEACHERS = 1 + 3;

        final List<Thread> threads = new ArrayList<>();
        final Random random = new Random();
        HashSet<Student> students = new HashSet<>(List.of(
                new Student("Student 1", 20, "IP-11"),
                new Student("Student 2", 20, "IP-11"),
                new Student("Student 3", 20, "IP-11"),
                new Student("Student 4", 20, "IP-11"),
                new Student("Student 5", 20, "IP-11")
        ));
        final Journal journal = new Journal(students);

//        teachers grade students
        for (int week = 0; week < WEEKS; week++) {
            for (int i = 0; i < TEACHERS; i++) {
                final Thread thread = new Thread(() -> {
                    for (Student student : students) {
                        final int grade = 1 + random.nextInt(100);
                        journal.addGradeForStudent(student, grade);
                    }
                });
                thread.start();
                threads.add(thread);
            }
        }

//        wait for them all to add grades
        for (Thread thread : threads) {
            thread.join();
        }
//        print each student's results at the end of all weeks
        for (Map.Entry<Student, List<Integer>> journalEntry : journal.getGrades().entrySet()) {
            final Student student = journalEntry.getKey();
            final List<Integer> grades = journalEntry.getValue();
            System.out.println(student.getName() + " " + student.getGroup() +
                    " received " + grades.size() +" grades: " + grades);
        }
    }
}
