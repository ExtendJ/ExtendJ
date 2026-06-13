// .result=COMPILE_FAIL
import java.util.*;

class Test {
	interface X<A> { ArrayList<A> execute(int a); }
	interface Y<A> { ArrayList<A> execute(int a); }
	
	@FunctionalInterface
	interface Exec<A, B> extends Y<A>, X<B> { }
}