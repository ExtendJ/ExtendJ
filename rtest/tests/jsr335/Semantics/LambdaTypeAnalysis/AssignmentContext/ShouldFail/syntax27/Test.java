// .result=COMPILE_FAIL
import java.util.*;

public class Test {
	public interface A {
		 public B m(int a);
	}
	
	public interface B {
		 public C m(int a, double b);
	}
	
	public interface C {
		 public D m();
	}
	
	public interface D {
		 public E m(int a);
	}
	
	public interface E {
		 public F m();
	}
	
	public interface F {
		 public int m(int a);
	}
	
	public static void main(String[] args) {
		A aInterface = a1 -> (a2, b1) -> () -> a3 -> () -> a4 -> b1; 
    }
}
