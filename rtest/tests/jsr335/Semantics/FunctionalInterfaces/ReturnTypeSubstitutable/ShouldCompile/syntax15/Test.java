// .result=COMPILE_PASS
import java.util.*;

class Test {
	interface X<A> { A execute(int a); }
	interface Y<B> { B execute(int a); }
	
	@FunctionalInterface
	interface Exec<A> extends Y<A>, X<A> { }
}