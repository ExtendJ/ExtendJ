// .result=COMPILE_FAIL
public class Test {
	public interface A {
		static void m() { }
	}
	
	public interface C extends A { }
	
	public class D  {
		public void someMethod() {
			C.m();
		}
	}
}