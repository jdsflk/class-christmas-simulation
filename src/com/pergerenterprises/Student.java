package com.pergerenterprises;

public class Student {
    public int id;
    public Student drawn;
    public boolean checked;

    public Student(int id) {
        this.id = id;

        drawn = null;
        checked = false;
    }

    // Checks if the drawn is the same student as this.
    public boolean drewItself() {
        if(drawn == null) {
            return false;
        }
        return id == drawn.id;
    }
}
