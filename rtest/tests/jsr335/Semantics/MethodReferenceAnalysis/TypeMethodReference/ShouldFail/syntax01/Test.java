// .result=COMPILE_FAIL

public class Test {
	
	class Pair<T, S> {
		T t;
		S s;
		public Pair(T t, S s) {
			this.t = t;
			this.s = s;
		}
		public T first() {
			return t;
		}
	}
	
	interface A {
		String m(Pair<Integer, String> p); 
	}
	
	
	A a = Pair::first;
}