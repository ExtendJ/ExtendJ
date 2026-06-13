// .result=COMPILE_FAIL
import java.io.FileReader;

public class Test {
	public interface TestInterface {
		public Exception functMethod(); 
	}
	
	public static void main(String[] args) {
		try {
			FileReader r = new FileReader("Hej.txt");
		} catch(Exception e) {
			Exception e2 = e = new Exception();
			TestInterface t = () -> e;
		}
    }
	
}