package com.calendarcreator.model;

import java.util.List;

public class Course {

    private int year;
    private List<String> units;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getUnits() {
        return units;
    }

    public void setUnits(List<String> units) {
        this.units = units;
    }
}
