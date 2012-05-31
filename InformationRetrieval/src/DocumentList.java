
public class DocumentList {
   private String document;
   private String id;
   public DocumentList(String document, String id) {
      this.document = document;
      this.id = id;
   }
   
   public String getName() {
      return document;
   }
   
   public String getID() {
      return id;
   }

}
