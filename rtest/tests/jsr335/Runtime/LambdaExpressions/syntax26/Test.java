// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.io.IOException;
import java.util.*;

public class Test {
	
	public interface A {
		void m() throws IOException;
	}
	
	public static void main(String[] arg) {
		A a = () -> {
			throw new IOException();
		};
		boolean caught = false;
		
		try {
			a.m();
		} catch(IOException e) {
			caught = true;
		}
		
		testTrue("Lambda", caught);
		
	}
}
