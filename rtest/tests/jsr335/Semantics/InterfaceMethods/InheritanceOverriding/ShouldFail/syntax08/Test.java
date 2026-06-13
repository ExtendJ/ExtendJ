// .result=COMPILE_FAIL
public class Test {
	
	interface B {
		void m(int i) { }
	}
	
	interface C extends B {
		static m(int i) { }
	}
}