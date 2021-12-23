
import java.io.FileWriter;
import java.io.IOException;


public class AddUserClass {
   private String name;
   private String uniqueID;
  
    

 public void assignValue(String name,String uniqueID){
        this.name=name;
        this.uniqueID=uniqueID;
       
   }
   
   

 public void newusersaveinfile(){
        try {
      FileWriter myWriter = new FileWriter("addnewuser.txt",true);
      myWriter.write("\nName:"+name);
      myWriter.write("\r\nUnique ID:"+uniqueID);
    
      myWriter.close();
      System.out.println("Matched");
    } catch (IOException e) {
      System.out.println("A Error occured");
    }
   }
}
