
public interface Test {
	default void printString(String s) {
		System.out.println(s);
	}
}