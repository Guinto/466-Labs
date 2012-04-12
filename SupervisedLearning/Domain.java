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
	
	public Domain(ArrayList<Vector> attributesAndCategory, int numCategories) {
		this.attributesAndCategory = attributesAndCategory;
		setNumCategories(numCategories);
	}
	
	public ArrayList<Domain> split() {
		ArrayList<Domain> splitDomain = new ArrayList<Domain>(getNumCategories());
		
		for (Vector v : attributesAndCategory) {
			int index = (int) v.last();
			Domain d = splitDomain.get(index);
			d.addVector(v);
		}
		
		return splitDomain;
	}
	
	public int size() {
		return attributesAndCategory.size();
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
	
	
}
