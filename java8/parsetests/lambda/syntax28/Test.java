import java.util.concurrent.Callable;

public class Test {
	public static void main(String[] args) {
        Object f = () -> {
        	if (true) return 12;
    		else {
	    		int result = 15;
	    		for (int i = 1; i < 10; i++)
	    			result *= i;
	    		return result;
    		}
    		} // Complex block body with returns
    }
}
