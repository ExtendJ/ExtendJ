// .result=COMPILE_FAIL
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.io.*;


public class Test {
	public interface A {
		ArrayList<Number> m();
	}
	public static void main(String[] arg) {
		A a = ArrayList<Integer>::new;
	}
}