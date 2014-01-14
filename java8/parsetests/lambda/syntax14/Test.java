import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
        Object f = () -> { System.gc(); }; // No parameters, void block body

    }
}
