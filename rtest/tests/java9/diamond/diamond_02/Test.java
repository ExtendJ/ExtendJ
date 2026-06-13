// .result=COMPILE_FAIL
/**
    From the Java 9 specification page 501: "It is a compile-time error if a class instance creation expression provides type
    arguments to a constructor but uses the diamond form for type arguments to the
    class."
    Note: does not test diamond access with anonymous classes
 */
 import java.util.*;

public class Test {
    public static void main(String[] args) {
        // This line should compile successfully
        TestClass<Integer> test1 = new TestClass<>(123);
        // This line should produce a compile-time error because it provides type
        // arguments to the constructor and uses the diamond form for type arguments
        TestClass<String> test2 = new <String>TestClass<>("Hello, World!") {};
    }
}
