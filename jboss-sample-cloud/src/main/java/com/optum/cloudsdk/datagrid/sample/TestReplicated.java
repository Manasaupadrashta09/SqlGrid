package com.optum.cloudsdk.datagrid.sample;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 
 */
public class TestReplicated {
      public static  void main(String[] args) throws Exception {
      ReplicatedCache dc = new ReplicatedCache();
      dc.saveData("cache2", "Hi This is Replicated Cache Example");
      dc.readData();
  }
}
