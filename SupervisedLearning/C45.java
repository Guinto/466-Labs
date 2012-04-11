import java.util.ArrayList;

/**
 * 
 * @author Trent Ellingsen
 *
 */
public class C45 {
	
	private double getEnthropy(Domain d) {
		ArrayList<Domain> domains = d.split();
		return  getEnthropy(domains.get(0).size(), domains.get(1).size());
	}
	
	private double getEnthropy(double a, double b) {
		return -a * Math.log(a)/Math.log(2) - -b * Math.log(b)/Math.log(2);
	}
}
