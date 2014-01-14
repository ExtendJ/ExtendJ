
public class Test {
	public static void main(String[] args) {
		Object f = (SomeInterface & AnotherInterface)(int x, double y) -> x + y * 45 - 5;
    }
}
