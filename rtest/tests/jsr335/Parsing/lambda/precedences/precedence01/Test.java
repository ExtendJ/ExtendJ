// .result=COMPILE_OUTPUT
// .options=XstructuredPrint
public class Test {
	public static void main(String[] args) {
		Integer[] a;
		//"a instanceof Integer" can be reduced early to an expression
		Object f = () -> a instanceof Integer[]; 
    }
}
