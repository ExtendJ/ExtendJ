// .result=COMPILE_FAIL


public class Test {
	interface A {
		default boolean equals(Object o) { return true; } 
	}
}