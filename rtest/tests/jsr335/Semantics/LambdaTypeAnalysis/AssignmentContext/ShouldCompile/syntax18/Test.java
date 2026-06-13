// .result=COMPILE_PASS
import java.util.*;

public class Test {
	public interface TestInterface<T, S> {
		public S functMethod(T t, S s); 
	}
	
	public static void main(String[] args) {
		TestInterface<Integer, String> t = args.length == 4 ? (a,b) -> b.substring(a) : (Integer a, String b) -> b.toString();
    }
}
