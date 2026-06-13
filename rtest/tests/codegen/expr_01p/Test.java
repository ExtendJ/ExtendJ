// Test that uninitialized variable is not accessed incorrectly
public class Test {
	public static void main(String[] args) {
		int i;
		boolean b = (false && (i > 0)) && (i == 0);
	}
}
