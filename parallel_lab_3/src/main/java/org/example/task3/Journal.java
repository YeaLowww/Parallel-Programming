package org.example.task3;

import java.util.*;
import java.util.stream.Collectors;

public class Journal {
    private final Map<Student, List<Integer>> grades;

    public Journal(Set<Student> students) {
        this.grades = students
                .stream()
                .collect(Collectors.toMap(
                        student -> student,
                        student -> new ArrayList<>()));
    }

    public void addGradeForStudent(Student student, Integer grade) {
        synchronized (student) {
            getGrades().get(student).add(grade);
        }
    }

    public Map<Student, List<Integer>> getGrades() {
        return grades;
    }
}
