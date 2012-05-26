import java.util.ArrayList;
import java.util.Hashtable;


public class Tools {
   private Hashtable<String, Document> words;
   private ArrayList<docList> docs;
   
   public Tools() {
      this.words = new Hashtable<String, Document>();
      this.docs = new ArrayList<docList>();
   }
   public int termFrequency(String word, PlainTextReader words) {
      return words.getWords().get(word);
   }
   
   public double idf(String word) {
      return Math.log((double) (docs.size()/words.get(word).getIDs().size()));
   }
   
   public double weight(String word) {
      return words.get(word).getTF() * words.get(word).getIDF();
   }
}
