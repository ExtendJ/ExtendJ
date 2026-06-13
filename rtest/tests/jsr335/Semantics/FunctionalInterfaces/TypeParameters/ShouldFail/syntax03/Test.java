// .result=COMPILE_FAIL
import java.util.*;
import java.io.Serializable;

class Test {
	interface X<A> { <T extends List<Map<?, A>>> void execute(int a); }
	interface Y<B> { <S extends List<Map<?, B>>> void execute(int a); }
	
	// This test previously failed because there was a bug that substitution was not
	// performed in bound lists.
	@FunctionalInterface
	interface Exec<A, B> extends Y<A>, X<B> { }
}
