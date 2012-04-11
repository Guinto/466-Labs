/**
 * 
 * @author Trent Ellingsen
 *
 */
public class C45 {
	
	private double getEnthropy(Domain d) {
		return 0;
	}
	
	private double getEnthropy(double a, double b) {
		return -a * Math.log(a)/Math.log(2) - -b * Math.log(b)/Math.log(2);
	}
}
