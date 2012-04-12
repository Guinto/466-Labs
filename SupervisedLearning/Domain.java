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
	}
	
	public Domain(ArrayList<Vector> attributesAndCategory) {
		this.attributesAndCategory = attributesAndCategory;
	}
	

	
	public ArrayList<Domain> split() {
		ArrayList<Domain> splitDomain = new ArrayList<Domain>(numCategories);
		
		for (Vector v : attributesAndCategory) {
			int index = (int) v.last();
			Domain d = splitDomain.get(index);
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
	
	
}
