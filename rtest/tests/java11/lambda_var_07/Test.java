// .result=EXEC_PASS

/*
    Var can be used in lambdas in java 11
*/
import java.util.*;
public class Test {
    public static void main (String[] args) {
        List<String> names = Arrays.asList("David", "Johannes", "Görel", "Idriss", "Bob");
        names.stream()
            .filter((var name) -> name.length() > 5)
            .map((var name) -> name.toUpperCase())
            .sorted()
            .forEach((var name) -> System.out.println("Hello, " + name));

    }
}