// .result=COMPILE_PASS
import java.util.ArrayList;

class Test {
	interface X { <S extends ArrayList<?>> ArrayList<?> execute(int a); }
	interface Y { <T extends ArrayList<?>> T execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}