// .result=COMPILE_PASS
import java.io.EOFException;
import java.io.IOException;
import java.util.*;


public class Test {
	
	interface A {
		int m(double i, String s);
	}
	
	public class Inner {
		public int m1(double d, String s) {
			return 3;
		}
	}
	
	public Inner[] getInners() {
		return new Inner[4];
	}
	
	public Test getTest() {
		return new Test();
	}
	
	public void testMethod() {
		
		A a = getTest().getInners()[0]::m1;
	}
	
	public int m1(double d, String s) {
		return 4;
	}
}