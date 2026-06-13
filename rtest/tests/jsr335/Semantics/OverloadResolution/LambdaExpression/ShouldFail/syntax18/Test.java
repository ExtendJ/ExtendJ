// .result=COMPILE_FAIL
// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.testTrue;

public class Test {
	
	interface A {
		int m(int a, int b); 
	}
	interface B {
		void m(int a, int b);
	}
	
	public static int m() {
		return 5;
	}
	
	public static void method(A a) {
	}
	
	public static void method(B b) {
	}
	
	public static void main(String[] args) {
		// Tests that non-pertinance for applicability works for conditional expressions
		method(args.length == 4 ? (int a, int b) -> m() : (a, b) -> m());
    }
}
