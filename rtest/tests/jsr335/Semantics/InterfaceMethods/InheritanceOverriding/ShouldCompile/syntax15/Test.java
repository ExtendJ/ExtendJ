// .result=COMPILE_PASS
public class Test {
	public interface A {
		static void m() {}
	}
	public interface B extends A {
		static void m() { }
	}
	
	public class Inner implements B {
		public void m() { }
	}

}