package com.optum.cloudsdk.datagrid.sample;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author 
 */

public class Resiliency {
       public  void resiliencyTask() throws Exception {
        Task task = new Task();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(task);

        try {
            System.out.println("Started.. the task");
            String result = future.get(10, TimeUnit.SECONDS);
            System.out.println("Finished! the task");
        } catch (TimeoutException e) {
            System.out.println("Terminated! task due to not reachable");
            task.cleanup();
        }
        executor.shutdownNow();
    }
}
