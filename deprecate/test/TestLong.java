package test;

import org.objectweb.asm.commons.GeneratorAdapter;

public class TestLong {

	public static void main(String[] args) {
		Object j = new Long(10);
		long j2 = ((Long)j).longValue();
		int i = (int) j2;
		System.out.println(i);
		double a=10;
		double n=30;
		while(a >= n) {
			System.out.println(i);
		}
	}
}
