// .result=COMPILE_PASS
import java.util.*;

public class Test {
	public interface TestInterface<T> {
		public String functMethod(int a); 
	}
	
	public static void main(String[] args) {
		TestInterface<String> t = (int a) -> {
			Runnable r = new Runnable() {
				public void run() { return; }
			};
			return "hej";
		};
	}
}
