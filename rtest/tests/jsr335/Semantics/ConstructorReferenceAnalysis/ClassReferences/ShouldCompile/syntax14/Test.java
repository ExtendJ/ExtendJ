// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;
import java.io.*;


public class Test {

	/*
	 * This test case currently fails due 
	 * to a bug in JastAddJ. See issue #74
	 * in the bitbucket repository. 
	 */
	
	public interface A {
		<T extends Integer> B m(T t);
	}
	
	public class B {
		public B(int i) {
		}
	}
	
	public void testMethod() {
		A a = B::new;
	}
	
	public static void main(String[] arg) {
		new Test().testMethod();
	}
}