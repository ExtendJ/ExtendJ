// .result=COMPILE_FAIL
public class Test {
	interface A {
		default void m(int i) { System.out.println("" + i + 1); }
	}
	interface B {
		default void m(int i) {  }
	}
	
	abstract class C {
		abstract void m(int i);
	}
	
	class D extends C implements A, B {

	}
}