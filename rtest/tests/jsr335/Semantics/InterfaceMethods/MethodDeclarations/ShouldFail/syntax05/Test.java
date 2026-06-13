// .result=COMPILE_FAIL


public class Test {
	interface A<T extends List<Boolean>> {
		static boolean m(T t) { 
			return false;
		} 
	}
}