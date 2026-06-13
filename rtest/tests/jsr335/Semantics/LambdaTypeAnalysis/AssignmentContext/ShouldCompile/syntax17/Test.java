// .result=COMPILE_PASS
import java.util.*;

public class Test {
	public interface TestInterface<T, S> {
		public S functMethod(T t, S s); 
	}
	
	public static void main(String[] args) {
		TestInterface<Integer, String> t = (a, b) ->  {
			int c = a + b.length();
			if(c > 3)
				return b;
			else
				return b + "tail";
		};
    }
}
