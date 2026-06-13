// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;
import java.io.*;


public class Test {

	interface A {
		ArrayList<Integer> m(ArrayList<Integer> a);
	}
	
	
	public static void main(String[] arg) {
		A a = ArrayList::new;
	}
}