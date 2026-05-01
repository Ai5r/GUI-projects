
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public class AllmemberClass {
     private String allmemberlist="";
     
     
    
      public void readallmemberlist(){
       try {
      AppDataStore dataStore = new AppDataStore();
      allmemberlist = dataStore.readDashboardAllMembers();
      System.out.println("Successfully read the list");
    } catch (IOException e) {
      System.out.println("An error occurred.");}
      
    }
      
      public String getMemberList(){
          return allmemberlist;
      }
}
