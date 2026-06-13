// .result=COMPILE_PASS

public class Test {
	interface A {
		int m(int a); 
	}
	
	public static void method(A a) {
		
	}
	
	public static void main(String[] args) {
		method((int a) -> {
			if(a < 4)
				return 5;
			else
				return 2;
		});
    }
}