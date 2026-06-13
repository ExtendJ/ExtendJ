// .result=COMPILE_PASS
public class Test {
	public interface A {
		int m();
	}
	
	public interface B {
		default int m() {
			return 2;
		}
	}
	
	public interface C extends B {

	}
	
	public interface D extends B {
	}

	public interface E extends C, D {

		default int m() {
			return 3;
		}
		
		default void testMethod() {
			int i = C.super.m();
		}
		
	}
}