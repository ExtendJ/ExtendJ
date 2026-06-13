// .result=COMPILE_PASS
import java.util.*;
import java.lang.StringBuilder;

class Test {
	interface X<A> { <T extends A> void execute(int a); }
	interface Y<B> { <S extends B> void execute(int a); }
	
	@FunctionalInterface
	interface Exec<A> extends Y<A>, X { }
	
	/* This test currently fails, because JastAddJ
	 * does not erase Raw type properly. Since X is a
	 * raw type in this case, the method in X should
	 * be erased, which means it will override the 
	 * generic method in Y. 
	 */
	
	public static void main(String[] arg) {
		Exec<Integer> e = a -> { };
	}
}