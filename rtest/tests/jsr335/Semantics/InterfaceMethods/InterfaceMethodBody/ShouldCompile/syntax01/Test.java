// .result=COMPILE_PASS

public class Test {
	public interface A {
		default void m() { }
		static void m2() { }
		void m3();
	}
}