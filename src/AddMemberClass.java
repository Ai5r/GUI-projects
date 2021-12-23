
import java.io.FileWriter;
import java.io.IOException;


public class AddMemberClass {
     private String usertype;
     private String name;
  
    

 public void assignValue(String type,String name){
        this.usertype=type;
        this.name=name;
       
   }
   
   

 public void addedmemberonfile(){
        try {
      FileWriter myWriter = new FileWriter("addedmember.txt",true);
       myWriter.write("\r\nName:"+name);
      myWriter.write("\nMethod:"+usertype);
     
      myWriter.close();
      System.out.println("Successfully add new amount");
    } catch (IOException e) {
      System.out.println("A Error found");
    }
   }
    
}
