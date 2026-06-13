// .result=COMPILE_PASS
public class Test {
	public interface A {
		void m();
	}
	public interface B {
		static void m() { }
	}
	public interface C extends A, B {
		
	}
}