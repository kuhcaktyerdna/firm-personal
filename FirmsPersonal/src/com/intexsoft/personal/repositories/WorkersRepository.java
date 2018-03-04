package com.intexsoft.personal.repositories;

import com.intexsoft.personal.comparators.BirthDateComparator;
import com.intexsoft.personal.comparators.EnteringDateComparator;
import com.intexsoft.personal.comparators.NameComparator;
import com.intexsoft.personal.workers.Worker;

import java.util.ArrayList;

public class WorkersRepository {
    public final static WorkersRepository INSTANCE = new WorkersRepository();
	private ArrayList<Worker> repository = new ArrayList<>();
    private int id = 0;

	 private WorkersRepository() {}

    public  ArrayList<Worker> getRepository() {
		return repository;
	}

	public static WorkersRepository getInstance(){
	     return INSTANCE;
    }

	public void addWorker(Worker newWorker){
        newWorker.setId(id);
        id++;
        repository.add(newWorker);
    }

    public void addWorkers(Worker[] newWorkers){
        for(Worker wrk : newWorkers){
            addWorker(wrk);
        }
    }

    public void addWorkers(ArrayList<Worker> newWorkers){
        for(Worker wrk : newWorkers){
            addWorker(wrk);
        }
    }

    public void removeWorker(int id){
        repository.remove(getWorkerById(id));
    }

    public Worker getWorkerById(int id){
        Worker worker = null;
        for(Worker wrk : repository){
            if(wrk.getId() == id)
                worker = wrk;
        }
        return worker;
    }

    public void removeWorkers(int[] ids){
        for(int i : ids){
            removeWorker(i);
        }
    }

    public void sortByName(){
        repository.sort(new NameComparator());
    }

    //code duplicate
    public void sortByBirthDate(){
        repository.sort(new BirthDateComparator());
    }

    public void sortByEnteringDate(){
        repository.sort(new EnteringDateComparator());
    }
}