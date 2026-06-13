// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.List;


public class Test<T> {
	int i = 2;
	
	public interface A {
		int m();
	}
	
	public static int m() {
		return 5;
	}
	
	public static void main(String[] arg) {
		A a = Test<Integer>::m;
	}
}