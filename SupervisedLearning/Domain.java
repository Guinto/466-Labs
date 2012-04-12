import java.util.ArrayList;

/**
 * 
 * @author Trent Ellingsen
 *
 */
public class Domain {
	
	private ArrayList<Vector> attributesAndCategory;
	private int numCategories;
	
	public Domain() {
		numCategories = 2;
		this.attributesAndCategory = new ArrayList<Vector>();
	}
	
	public Domain(ArrayList<Vector> attributesAndCategory, int numCategories) {
		this.attributesAndCategory = attributesAndCategory;
		setNumCategories(numCategories);
	}
	
	public Domain[] split() {
		Domain[] splitDomain = new Domain[getNumCategories()];
		for(int i = 0; i < getNumCategories(); i++)
		   splitDomain[i] = new Domain();
		
		for (Vector v : attributesAndCategory) {
			int index = (int) v.last();
			Domain d = splitDomain[index-1];
			d.addVector(v);
		}
		
		return splitDomain;
	}
	
	public Domain[] split(int attribute, int numattr) {
      Domain[] splitDomain = new Domain[numattr];
      for(int i = 0; i < numattr; i++)
         splitDomain[i] = new Domain();

      for (Vector v : attributesAndCategory) {
         int index = (int) v.get(attribute);
         Domain d = splitDomain[index-1];
         d.addVector(v);
      }
      
      return splitDomain;
   }
	
	
	public void addVector(Vector v) {
		attributesAndCategory.add(v);
	}

	public int getNumCategories() {
		return numCategories;
	}

	public void setNumCategories(int numCategories) {
		this.numCategories = numCategories;
	}

   public double size() {
      return attributesAndCategory.size();
   }
   
   public ArrayList<Vector> getVectors() {
      return attributesAndCategory;
   }
	
	
}