
import java.io.IOException;


public class AddMemberClass {
     private String usertype;
     private String name;
  
    

 public void assignValue(String type,String name){
        this.usertype=type;
        this.name=name;
       
   }
   
   

 public boolean addedmemberonfile(){
        try {
      AppDataStore dataStore = new AppDataStore();
      dataStore.saveAddedMember(usertype, name);
      System.out.println("Successfully add new amount");
      return true;
    } catch (IOException e) {
      System.out.println("A Error found");
      return false;
    }
   }
    
}
