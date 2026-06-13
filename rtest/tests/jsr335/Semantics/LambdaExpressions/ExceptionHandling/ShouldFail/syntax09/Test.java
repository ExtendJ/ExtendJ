// .result=COMPILE_FAIL
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

// Taken from JSR335, part A

public class Test {
	interface G1<E extends Exception> {
		Object m() throws E;
	}
	interface G2 {
		String m() throws Exception;
	}
	interface G extends G1<FileNotFoundException>, G2 {}

	// G has descriptor ()->String throws FileNotFoundException
	
	public static void main(String[] args) {
		G e = () -> { BufferedReader r = new BufferedReader(null); r.close(); return "Hej"; };
	}
}

