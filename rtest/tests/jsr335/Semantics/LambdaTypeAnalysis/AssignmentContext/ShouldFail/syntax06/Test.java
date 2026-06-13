// .result=COMPILE_FAIL
import java.util.*;

public class Test {
	public interface TestInterface {
		 public int m();
	}
	
	public static void main(String[] args) {
		ArrayList t = args.length==4 ? () -> 4 : () -> 5; 
    }
}
