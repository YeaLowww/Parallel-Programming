package org.example.task3;

public class Student {
    private final String name;
    private final int age;
    private final String group;

    public Student(String name, int age, String group) {
        this.name = name;
        this.age = age;
        this.group = group;
    }

    public String getName() {
        return name;
    }


    public String getGroup() {
        return group;
    }
}
