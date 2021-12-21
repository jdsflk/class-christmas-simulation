package com.pergerenterprises;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation {
    private final int classSize;
    private final boolean isVerbose;

    public Simulation(int classSize, boolean isVerbose) {
        this.classSize = classSize;
        this.isVerbose = isVerbose;
    }

    public Integer run() {
        ArrayList<Student> students = generateStudents(classSize);
        while(true){
            if(draw(students)) {
                break;
            }
        }

        if (isVerbose) {
            System.out.println();
            students.forEach(student -> {
                System.out.println(student.id + " will gift " + student.drawn.id);
            });
            System.out.println();
        }

        Map<Integer, Integer> cycles = getCycles(students);

        if (isVerbose) {
            cycles.forEach((k, v) -> {
                System.out.println("There are " + v + " " + k + " long cycles");
            });
        }
        return cycles.size();
    }

    private Map<Integer, Integer> getCycles(ArrayList<Student> students) {
        Map<Integer, Integer> cycles = new HashMap<Integer, Integer>();
        students.forEach(student -> {
            if (!student.checked) {
                Integer cycleSize = getCycleSize(student, null, 0);
                if (cycles.containsKey(cycleSize)) {
                    cycles.put(cycleSize, cycles.get(cycleSize) + 1);
                } else {
                    cycles.put(cycleSize, 1);
                }
            }
        });
        return cycles;
    }

    private ArrayList<Student> generateStudents(int numberOfStudents) {
        ArrayList<Student> students = new ArrayList<Student>();

        for (int i = 0; i < numberOfStudents; i++) {
            students.add(new Student(i));
        }
        return students;
    }

    // Returns false if the last student drew itself.
    private boolean draw(ArrayList<Student> students) {

        // Create a copy of the students.
        ArrayList<Student> hat = new ArrayList<Student>(students);

        for (Student student : students) {
            int drawnIndex; // The index of the drawn in the hat.
            do {
                drawnIndex = ThreadLocalRandom.current().nextInt(0, hat.size());
                student.drawn = hat.get(drawnIndex);
                if (student.drewItself() && hat.size() == 1) {
                    return false;
                }
            } while (student.drewItself());

            // Removing the drawn student.
            hat.remove(drawnIndex);
        }
        return true;
    }

    private Integer getCycleSize(Student firstStudent, Student currentStudent, int cycleSize) {
        if (currentStudent == null) {
            firstStudent.checked = true;
            return getCycleSize(firstStudent, firstStudent.drawn, cycleSize + 1);
        } else if (firstStudent == currentStudent) {
            currentStudent.checked = true;
            return cycleSize;
        }
        currentStudent.checked = true;
        return getCycleSize(firstStudent, currentStudent.drawn, cycleSize + 1);
    }
}
