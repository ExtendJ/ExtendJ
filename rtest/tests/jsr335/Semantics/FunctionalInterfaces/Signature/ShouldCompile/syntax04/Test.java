// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;


class Test {
	interface X { int execute(int i, double d, float a); }
	interface Y { int execute(int q, double b, float h); }
	interface Z extends Y { int execute(int q, double b, float h); }
	interface Q extends Z { int execute(int q, double b, float h); }
	
	@FunctionalInterface
	interface Exec extends Y, X, Z, Q { }
}