package com.intexsoft.personal.services;

import com.intexsoft.personal.workers.Worker;
import java.util.ArrayList;

public interface IService {

    void addWorker(Worker newWorker);

    void addWorkers(Worker[] newWorkers);

    void addWorkers(ArrayList<Worker> newWorkers);

    void removeWorker(int id);

    void removeWorkers(int[] ids);

    Worker getWorkerById(int id);

}
