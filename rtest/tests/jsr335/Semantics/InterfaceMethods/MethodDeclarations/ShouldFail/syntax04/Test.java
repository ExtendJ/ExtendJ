// .result=COMPILE_FAIL


public class Test {
	interface A<T extends List<Boolean>> {
		static boolean m(Object o) { 
			if(o instanceof T) 
				return true;
			return false;
		} 
	}
}