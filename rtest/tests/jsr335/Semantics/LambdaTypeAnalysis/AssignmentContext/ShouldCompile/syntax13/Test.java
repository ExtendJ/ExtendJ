// .result=COMPILE_PASS
import java.util.*;

public class Test {
	public interface TestInterface {
		public void functMethod(int a, double b, ArrayList<Map<Integer, String>> c); 
	}
	
	public static void main(String[] args) {
		TestInterface t = (int a, double c, ArrayList<Map<Integer, String>> b) ->  { };
    }
}
