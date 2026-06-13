// .result=COMPILE_OUTPUT
// .options=XstructuredPrint
public class Test {
	public static void main(String[] args) {
		int a, b;
		//"(a, b) -> arrayAccess[a - b]" can be reduced early to a lambda
		Object f = (a, b) -> arrayAccess[a - b]::length;
    }
}
