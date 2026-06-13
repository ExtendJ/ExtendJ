// .result=COMPILE_PASS

public class Test {
	
	interface A {
		int[] m(int i);
	}
	
	public static void main(String[] arg) {
		A a = int[]::new;
	}

}