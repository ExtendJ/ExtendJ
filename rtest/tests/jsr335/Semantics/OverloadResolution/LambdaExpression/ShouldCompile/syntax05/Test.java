// .result=COMPILE_PASS

public class Test {
	interface A {
		void m(int a); 
	}
	
	public static void method(A a) {
		
	}
	
	public static void main(String[] args) {
		method((int i) -> System.out.println(i));
    }
}