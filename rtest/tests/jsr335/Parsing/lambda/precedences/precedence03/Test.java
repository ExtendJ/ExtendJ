// .result=COMPILE_OUTPUT
// .options=XstructuredPrint
public class Test {
	public static void main(String[] args) {
		int a, b;
		//"(Integer)() -> a" can be reduced early to a cast expression
		Object f = (Integer)() -> a + b; 
    }
}
