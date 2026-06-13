// .result=COMPILE_PASS
import java.io.*;

public class Test {
	public interface A {
		public void functMethod() throws IOException; 
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
