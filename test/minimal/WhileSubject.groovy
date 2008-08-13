/**
 * 
 */
package minimal



/**
 * @author chanwit
 *
 */
public class WhileSubject{

	def while_001() {
		int a = 10
		int n = 0
		while(a >= n) {
			a--;
		}
		return a
	}
	
	def while_002() {
		double a = 10
		double n = 0
		while(a >= n) {
			a--;
		}
		return a
	}
			
	static main(args) {
		println new WhileSubject().while_001()
	}
	
}
