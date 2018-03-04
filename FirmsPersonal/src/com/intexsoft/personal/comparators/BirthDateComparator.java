package com.intexsoft.personal.comparators;

import com.intexsoft.personal.workers.Worker;

import java.util.Comparator;

public class BirthDateComparator implements Comparator<Worker> {

    @Override
    public int compare(Worker w1, Worker w2) {
        if (w1 != null && w2 != null) {
            return w1.getBirthDate().compareTo(w2.getBirthDate());
        }
        else if (w1 != null && w2 == null) {
            return 1;
        } else {
            return -1;
        }
    }
}