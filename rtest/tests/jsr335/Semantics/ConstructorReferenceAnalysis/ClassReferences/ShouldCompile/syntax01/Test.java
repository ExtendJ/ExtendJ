// .result=COMPILE_PASS

public class Test {
	
	interface A {
		String m();
	}
	
	public static void main(String[] arg) {
		A a = String::new;
	}

}