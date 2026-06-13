// .result=COMPILE_FAIL


public class Test {
	interface A {
		default int m(int i) {return i;  }
	}
	
	
	class G implements A {
		public int m(int i) {
			String s = A.super.m(i);
			return A.super.m(i);
		}
	}
}