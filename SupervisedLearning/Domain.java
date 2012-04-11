import java.util.ArrayList;

/**
 * 
 * @author Trent Ellingsen
 *
 */
public class Domain {
	
	private ArrayList<Vector> attributesAndCategory;
	
	public ArrayList<Domain> split() {
		ArrayList<Domain> splitDomain = new ArrayList<Domain>();
		
		double firstCategory = attributesAndCategory.get(0).last();
		for (Vector v : attributesAndCategory) {
			//if ()
		}
		
		return splitDomain;
	}
}
