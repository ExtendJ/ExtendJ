// Test that errors are reported for all compilation units.
// .result=COMPILE_ERR_OUTPUT
// .source_order=A.java,Test.java
public class Test {
	public static void main(String[] args) {
		System.out.println(A.s(arsg));  // Error: Typo in variable name.
	}
}
