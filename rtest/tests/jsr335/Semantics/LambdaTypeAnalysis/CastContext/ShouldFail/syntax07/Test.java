// .result=COMPILE_FAIL

public class Test {
	
	public interface A {
		public int m(int a);
	}
	
	public interface B {
		public int m(int a);
	}
	
	public int someMethod(A a) {
		return a.m(3);
	}
	
	public static void main(String[] args) {
		System.out.println("" + someMethod((B)a -> a + 5));
    }
}
