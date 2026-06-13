// .result=COMPILE_PASS
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

// Taken from JSR335, part A

public class Test {
	interface G1<E extends IOException> {
		Object m() throws E;
	}
	interface G2 {
		String m() throws Exception;
	}
	interface G extends G1, G2 {}

	// G has descriptor ()->String throws IOException
	
	public static void main(String[] args) {
		G g = () -> { BufferedReader r = new BufferedReader(null); r.close(); return "Hej"; };
	}
}
