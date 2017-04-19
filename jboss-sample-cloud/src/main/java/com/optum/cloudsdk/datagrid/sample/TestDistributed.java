package com.optum.cloudsdk.datagrid.sample;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 
 */
public class TestDistributed {
  public static  void main(String[] args) throws Exception {
      DistributedCache dc = new DistributedCache();
      dc.saveData("cache1", "Hi This is Distributed Cache Example");
      dc.readData("cache1");
  }
}

