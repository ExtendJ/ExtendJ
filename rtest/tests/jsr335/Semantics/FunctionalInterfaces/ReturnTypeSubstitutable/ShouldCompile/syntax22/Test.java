// .result=COMPILE_PASS
import java.util.*;

class Test {
	interface X<A> { <B> Object execute(int a); }
	interface Y<B> { <A> A execute(int a); }
	
	/*
	 A should be erased to object, and thus 
	 should this test pass. 
	 
	 This test currently fails though, because JastAddJ
	 do not erase Raw types properly.
	 */
	@FunctionalInterface
	interface Exec extends Y, X<Integer> { }
	
	public static void main(String[] arg) {
		Exec e = a -> 4;
	}
}