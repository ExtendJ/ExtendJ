// .result=COMPILE_FAIL
import java.util.*;


public class Test {
	public interface A {
		int[] m(int i);
	}
	
	public interface B {
		long[] m(short i);
	}
	
	public static void main(String[] arg) {
		A a = int[]::new;
		B b = a::m;
		int[] arr = a.m(10);
		for(int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}
		for(int i = 0; i < arr.length; i++) {
		}		
	}
}