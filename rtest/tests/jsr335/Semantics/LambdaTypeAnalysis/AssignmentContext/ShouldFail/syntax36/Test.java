// .result=COMPILE_FAIL
import java.util.*;
import java.lang.StringBuilder;

public class Test {
	interface A { void m(String... a); }
	
	A a = (String s) -> { };
	
}