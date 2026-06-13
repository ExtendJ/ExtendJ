// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.io.*;


public class Test {

	interface A {
		ArrayList<Integer> m(ArrayList<String> a);
	}
	
	
	public static void main(String[] arg) {
		A a = ArrayList::new;
	}
}