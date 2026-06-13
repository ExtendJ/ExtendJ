// .result=COMPILE_PASS
import java.util.*;

public class Test {
	public interface TestInterface {
		public ArrayList<Integer> functMethod(); 
	}
	
	public int method(int a, double b) {
		return a;
	}
	
	public static void main(String[] args) {
		TestInterface t = () ->  new ArrayList<Integer>();
    }
}
