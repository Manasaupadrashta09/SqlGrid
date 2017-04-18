package com.optum.cloudsdk.datagrid.sample;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.concurrent.Callable;

/**
 *
 * @author 
 */
class Task implements Callable<String>, MyTask {

   
    public String call() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void cleanup() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

