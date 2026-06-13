// .result=COMPILE_FAIL


public class Test {
	
	interface A {
		void m(int i, String s);
	}
	
	public static void main(String[] args) {
		A a = (_, s) -> { };
    }
}