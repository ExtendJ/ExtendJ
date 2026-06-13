// .result=COMPILE_FAIL


public class Test {
	interface A {
		default synchronized void m() { 
			
		} 
	}
}