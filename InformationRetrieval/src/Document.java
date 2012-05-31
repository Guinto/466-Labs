
public class Document {
   private int id;
   private double tf;
   private double weight;

   public Document(int id, double tf, double weight) {
      this.id = id;
      this.tf = tf;
      this.weight = weight;
   }
   
   public void setweight(double weight) {
      this.weight = weight;
   }
   
   public int getid() {
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
