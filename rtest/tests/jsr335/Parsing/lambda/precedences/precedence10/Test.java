// .result=COMPILE_OUTPUT
// .options=XstructuredPrint
public class Test {
	public static void main(String[] args) {
		int a, b;
		boolean c, d;
		//Check precedence of various operators (not, postincr, noteq, lt, oror, xor, and, or, cast, div, andand, gt, minus)
		Object f = (e, f, g) -> !a++ != a < b || c ^ d & a | (Integer)b / 5 && b > a - 3; 
    }
}
