
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
      AppDataStore dataStore = new AppDataStore();
      dataStore.saveAddedUser(name, uniqueID);
      System.out.println("Matched");
    } catch (IOException e) {
      System.out.println("A Error occured");
    }
   }
}
