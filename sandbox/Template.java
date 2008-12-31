
public class Template {
	
	public static void a(int i, int j, int k) {
		System.out.println(i);
		System.out.println(j);
		System.out.println(k);		
	}
	
	public static void b(Integer i, Integer j, Integer k) {
		System.out.println(i);
		System.out.println(j);
		System.out.println(k);
	}	
	
	public static void main(String[] args) {
		a(1,2,3);
		b(1,2,3);
	}

}
