import java.util.ArrayList;


public class KeyWord {
   private ArrayList<Document> docs;
   private double idf;
   
   public KeyWord(String docID) {
      this.docs = new ArrayList<Document>();
   }
   
   public KeyWord(ArrayList<Document> docs, double idf) {
      this.docs = docs;
      this.idf = idf;
   }
   
   public ArrayList<Document> getdocs() {
      return docs;
   }
   
   public void addID(String id, double tf, double weight) {
      docs.add(new Document(id, tf, weight));
   }
   
   
   public double getIDF() {
      return idf;
   }
   
   public void setidf(double idf) {
      this.idf = idf;
   }
   
   public Document getid(String id) {
      int i = 0;
      for(i = 0; i < docs.size(); i++) {
         if(docs.get(i).getid().equals(id)) {
            return docs.get(i);
         }
      }
      return null;
   }
   

}
