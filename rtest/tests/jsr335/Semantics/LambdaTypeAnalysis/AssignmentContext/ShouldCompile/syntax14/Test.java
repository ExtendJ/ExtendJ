// .result=COMPILE_PASS
import java.util.*;

public class Test {
	public interface TestInterface<T> {
		public void functMethod(T t); 
	}
	
	public static void main(String[] args) {
		TestInterface t = (Object b) ->  { };
    }
}
