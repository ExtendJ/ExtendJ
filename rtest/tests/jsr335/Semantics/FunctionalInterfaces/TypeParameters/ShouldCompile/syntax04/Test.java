// .result=COMPILE_PASS
import java.util.*;
import java.io.Serializable;

class Test {
	interface X<A> { <T extends A> void execute(int a); }
	interface Y<B> { <S extends B> void execute(int a); }
	
	@FunctionalInterface
	interface Exec<A> extends Y<A>, X<A> { }
}