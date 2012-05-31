
public class DocumentList {
   private String document;
   private int id;
   public DocumentList(String document, int id) {
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
