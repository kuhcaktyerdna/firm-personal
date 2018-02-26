package com.intexsoft.comparators;

import com.intexsoft.workers.Worker;

import java.util.Comparator;

public class EnteringDateComparator implements Comparator<Worker> {

    @Override
    public int compare(Worker w1, Worker w2) {
        if (w1 != null && w2 != null) {
            return w1.getEnteringDate().compareTo(w2.getEnteringDate());
        }
        else if (w1 != null && w2 == null) {
            return 1;
        } else {
            return -1;
        }
    }
}