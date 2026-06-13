// .result=COMPILE_PASS
import java.util.ArrayList;

class Test {
	interface X {  Integer execute(int a); }
	interface Y {  Number execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}