package com.intexsoft.personal;

import com.intexsoft.personal.services.Service;

import java.io.*;
public class Runner {

    public static void main(String args[]){
        run();
    }

    public static void run(){
        File file = new File("workers.txt");
        Service service = Service.getInstance();
        service.readList(file);
        service.sortByBirthDate();
        service.writeList();
    }
}
