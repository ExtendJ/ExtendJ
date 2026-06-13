// .result=COMPILE_PASS
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Test {
	interface X { <S extends List<T> & Serializable, T extends Integer>  List<? extends List<? extends Integer>> execute(int a); }
	interface Y { <T extends Serializable & List<S>, S extends Integer>  List<? extends T>                       execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}