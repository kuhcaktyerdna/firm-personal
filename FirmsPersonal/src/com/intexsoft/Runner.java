package com.intexsoft;

import java.io.*;
public class Runner {

    public static void main(String args[]){
        run();
    }

    public static void run(){
        File file = new File("workers.txt");
        Service service = Service.getInstance();
        service.readList(file);
        service.writeList();
    }
}
