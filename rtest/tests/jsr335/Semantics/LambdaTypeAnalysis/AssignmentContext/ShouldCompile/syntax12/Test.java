// .result=COMPILE_PASS
import java.util.*;

public class Test {
	public interface TestInterface {
		public void functMethod(int a); 
	}
	
	public static void main(String[] args) {
		TestInterface t = a ->  {
			if(a > 4) 
				System.out.println(">4");
			else
				return;
			
			if(a > 8) {
				int c = a;
				System.out.println("" + (c + 4));
			}
		};
    }
}
