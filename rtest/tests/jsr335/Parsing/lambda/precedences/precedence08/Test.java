// .result=COMPILE_OUTPUT
// .options=XstructuredPrint
public class Test {
	public static void main(String[] args) {
		int a, b;
		boolean c, d;
		//Check precedence of various operators (unary, minus, andand, not, or, oror, comp)
		Object f = () -> -a - b && !c | !d || ~a;
    }
}
