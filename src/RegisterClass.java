
import java.io.FileWriter;
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
public class RegisterClass {
    
   private String name;
   private String phoneNo;
   private String email;
   private String password;
    

 public void assignValue(String name,String phoneNo,String email,String password){
        this.name=name;
        this.phoneNo=phoneNo;
        this.email=email;
        this.password=password;
   }
   
   

 public void userdatasave(){
        try {
      FileWriter myWriter = new FileWriter("users.txt",true);
      myWriter.write("\nName:"+name);
      myWriter.write("\r\nPhone:"+phoneNo);
      myWriter.write("\r\nEmail:"+email);
      myWriter.close();
      System.out.println("Successfully Register");
    } catch (IOException e) {
      System.out.println("A Error occured");
    }
   }
}
