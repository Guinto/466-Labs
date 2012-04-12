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
	
	public ArrayList<Domain> split(int attribute, int numattr) {
      ArrayList<Domain> splitDomain = new ArrayList<Domain>(numattr);
      
      for (Vector v : attributesAndCategory) {
         int index = (int) v.get(attribute);
         Domain d = splitDomain.get(index);
         d.addVector(v);
      }
      
      return splitDomain;
   }
	
	public double getYesRatio() {
	   int count = 0;
	   for(Vector v: attributesAndCategory) {
	      if(v.last() == 1) {
	         count++;
	      }
	   }
	   return count/size();
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