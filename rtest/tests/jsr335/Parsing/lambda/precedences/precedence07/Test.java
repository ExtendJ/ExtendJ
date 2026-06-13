// .result=COMPILE_OUTPUT
// .options=XstructuredPrint
public class Test {
	public static void main(String[] args) {
		int a;
		boolean b;
		//"a + () -> b" can be reduced early to an add expression
		Object f = a + () -> b || false; 
    }
}
