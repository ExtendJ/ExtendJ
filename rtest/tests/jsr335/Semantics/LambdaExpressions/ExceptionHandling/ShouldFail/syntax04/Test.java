// .result=COMPILE_FAIL
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

// Taken from JSR335, part A

class FooException extends Exception { }
class FooSubException extends FooException { }

public class Test {
	interface A {
		List<String> foo(List<String> arg) throws IOException, FooSubException;
	}
	interface B {
		List foo(List<String> arg) throws EOFException, FooException, TimeoutException;
	}
	interface C {
		List foo(List arg) throws Exception;
	}
	interface D extends A, B {}
	interface E extends A, B, C {}

	// D has descriptor (List<String>)->List<String> throws EOFException, FooSubException
	// E has descriptor (List)->List throws EOFException, FooSubException
	
	public static void main(String[] args) {
		D d = a -> { throw new TimeoutException(); };
	}
}
