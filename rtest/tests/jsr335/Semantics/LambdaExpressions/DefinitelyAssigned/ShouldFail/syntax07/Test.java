// .result=COMPILE_FAIL

public class Test {
	public interface A {
		A m();
	}
	
	public static void main(String[] arg) {
		A a = () -> () -> a.m();
	}
}