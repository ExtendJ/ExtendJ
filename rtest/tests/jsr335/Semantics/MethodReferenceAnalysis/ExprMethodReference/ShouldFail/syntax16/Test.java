// .result=COMPILE_FAIL
import java.io.EOFException;
import java.io.IOException;


public class Test {
	public interface A {
		int m();
	}
	
	public abstract class InnerAbstract {
		public abstract int method();
	}
	
	public class Inner extends InnerAbstract {

		@Override
		public int method() {
			return 5;
		}
		
		public void testMethod() {
			A a = super::method;
		}
		
	}
}