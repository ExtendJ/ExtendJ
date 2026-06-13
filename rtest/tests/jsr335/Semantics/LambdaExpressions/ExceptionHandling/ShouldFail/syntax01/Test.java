// .result=COMPILE_FAIL
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Test {
	public interface A {
		public void functMethod(); 
	}
	public interface B {
		public void functMethod() throws IOException;
	}
	
	public interface C extends A, B { };
	
	public static void main(String[] args) {
		C c = () -> {
			BufferedReader reader = new BufferedReader(new FileReader("Some file"));
		};
    }
}
