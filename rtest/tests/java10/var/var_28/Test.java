//.result=COMPILE_FAIL

import java.util.function.Consumer;

public class Test {
	public static void main(String[] args) {
		var simpleReference = Test::someMethod;
    }
    private static void someMethod(int value) {
        System.out.println(value);
    }
}