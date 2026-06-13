// .result=COMPILE_PASS

public class Test {
	interface A {
		B m(int a); 
	}
	
	interface B {
		String m(int a, int b);
	}
	
	public static void method(int i, A a) {
		
	}
	
	public static void main(String[] args) {
		method(45, i -> {
			if(i < 5)
				return (a, b) -> "1 " + (a + b);
			else
				return (a, b) -> "2 " + (a + b);
		});
		
		method(10, i -> {
			method(i, i2 -> (a, b) -> "string");
			return (a, b) -> "string";
		});
		
		method(10, (int i) -> {
			if(i < 5)
				return (int a, int b) -> "1 " + (a + b);
			else
				return (int a, int b) -> "2 " + (a + b);
		});
    }
}