// .result=COMPILE_PASS
import java.util.*;

public class Test {
	public interface TestInterface {
		public double functMethod(int a); 
	}
	
	public static void main(String[] args) {
		TestInterface t = a ->  a;
    }
}
