// .result=COMPILE_PASS
import java.util.ArrayList;

class Test {
	interface X {  void execute(int a); }
	interface Y {  void execute(int a); }
	
	@FunctionalInterface
	interface Exec extends Y, X { }
}