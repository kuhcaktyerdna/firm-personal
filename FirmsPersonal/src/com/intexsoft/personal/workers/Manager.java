package com.intexsoft.personal.workers;

import java.util.ArrayList;
import java.util.Date;

public class Manager extends Worker {

    private final ArrayList<Worker> subordinates = new ArrayList<>();
    private int subId = 0;

    public Manager(String fullName, Date birthDate, Date enteringDate) {
        super();
    }


    public ArrayList<Worker> getSubordinates() {
        return subordinates;
    }

    public void addSubordinate(Worker subordinate) {
        subordinate.setSubId(subId);
        subId++;
        subordinates.add(subordinate);
    }

    public Worker getSubordinateById(int subId) {
        Worker subordinate = null;
        for (Worker w : subordinates) {
            if (w.getSubId() == subId) {
                subordinate = w;
            }
        }
        return subordinate;
    }

    public void removeSubordinate(int subId) {
        subordinates.remove(getSubordinateById(subId));
    }
}
