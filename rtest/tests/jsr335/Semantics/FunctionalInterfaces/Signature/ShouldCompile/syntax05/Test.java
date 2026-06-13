// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;


class Test {
	interface X { <S> int execute(S a); }
	interface Y { <T> int execute(T a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}