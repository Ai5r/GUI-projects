
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
      File list = new File("memberlist.txt");
      Scanner scanMember= new Scanner(list);
      allmemberlist="";
      while (scanMember.hasNext()) {
         allmemberlist  += scanMember.nextLine()+"\n";
        
      }
     System.out.println("Successfully read the list");
       scanMember.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");}
      
    }
      
      public String getMemberList(){
          return allmemberlist;
      }
}
