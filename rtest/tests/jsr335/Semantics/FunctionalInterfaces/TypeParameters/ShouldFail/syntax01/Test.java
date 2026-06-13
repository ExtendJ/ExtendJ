// .result=COMPILE_FAIL
import java.util.*;
import java.io.Serializable;

class Test {
	interface X { <T extends Serializable> void execute(int a); }
	interface Y { <S extends List<Integer> & Serializable> void execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}