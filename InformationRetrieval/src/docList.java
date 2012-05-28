
public class docList {
   private String document;
   private int id;
   public docList(String document, int id) {
      this.document = document;
      this.id = id;
   }
   
   public String getName() {
      return document;
   }
   
   public int getID() {
      return id;
   }

}
