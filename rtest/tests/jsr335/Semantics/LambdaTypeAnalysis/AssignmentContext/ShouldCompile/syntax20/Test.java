// .result=COMPILE_PASS
import java.util.*;

public class Test {
	public interface TestInterface {
		public InnerTestInterface functMethod(int a); 
	}
	
	public interface InnerTestInterface {
		public double functMethod(double a);
	}
	
	public static void main(String[] args) {
		TestInterface t = a -> b -> b + 4.5;
		t = a -> {
			if(a > 3) { 
				return (double b) -> {
					for(int i = 0; i < 4; i++)
						b += 4.5;
					if(b > 8.9)
						return b;
					else
						return 3.0;
				};
			}
			else {
				return g -> {
					if(g > 4.0)
						return 4.0;
					else
						return g;
				};
			}
		};
    }
}
