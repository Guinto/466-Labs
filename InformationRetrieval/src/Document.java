
public class Document {
   private String id;
   private double tf;
   private double weight;

   public Document(String id, double tf, double weight) {
      this.id = id;
      this.tf = tf;
      this.weight = weight;
   }
   
   public void setweight(double weight) {
      this.weight = weight;
   }
   
   public String getid() {
      return id;
   }
   
   public double getWeight() {
      return weight;
   }
   
   public void settf(double tf) {
      this.tf = tf;
   }
   
   public double getTF() {
      return tf;
   }
}
