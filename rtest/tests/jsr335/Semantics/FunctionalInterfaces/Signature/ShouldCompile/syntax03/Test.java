// .result=COMPILE_PASS
import java.util.ArrayList;
import java.util.List;


class Test {
	interface X { int execute(); }
	interface Y { int execute(); }
	interface Z extends Y { int execute(); }
	interface Q extends Z { int execute(); }
	
	@FunctionalInterface
	interface Exec extends Y, X, Z, Q { }
}