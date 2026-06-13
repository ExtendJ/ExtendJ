// .result=COMPILE_FAIL
public class Test {
	public interface A {
		default void m() {}
	}
	public interface B extends A {
		default void m() { }
	}
	public interface C extends A {
		default void m() { }
	}
	
	public class Inner implements B, C { }

}