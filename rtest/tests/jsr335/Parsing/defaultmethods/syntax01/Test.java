// .result=COMPILE_OUTPUT
// .options=XstructuredPrint

public interface Test {
	default void printString(String s) {
		System.out.println(s);
	}
}