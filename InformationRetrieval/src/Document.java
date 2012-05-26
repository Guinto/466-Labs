import java.util.ArrayList;


public class Document {
   private ArrayList<Integer> docIDs;
   private double tf;
   private double idf;
   private double weight;
   
   public Document(int docID) {
      this.docIDs = new ArrayList<Integer>();
   }
   
   public ArrayList<Integer> getIDs() {
      return docIDs;
   }
   
   public void addID(int id) {
      docIDs.add(id);
   }
   
   public double getTF() {
      return tf;
   }
   
   public double getIDF() {
      return idf;
   }
   
   public double getWeight() {
      return weight;
   }
   
   public void settf(double tf) {
      this.tf = tf;
   }
   
   public void setidf(double idf) {
      this.idf = idf;
   }
   
   public void setweight(double weight) {
      this.weight = weight;
   }
}
