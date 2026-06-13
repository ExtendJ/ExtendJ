// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.io.*;


public class Test {

	public interface A {
		<T> B m(T t);
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