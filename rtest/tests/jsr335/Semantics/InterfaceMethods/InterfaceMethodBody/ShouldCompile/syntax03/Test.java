// .result=COMPILE_PASS
public class Test {
	interface A {
		int a = 5;
		default int m() {
			return 5;
		}
		
		default void m2() {
			return;
		}
		
		default String m3() {
			if(a == 4)
				return "yes";
			else
				return "no";
		}
	}
}