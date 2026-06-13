// .result=EXEC_PASS

/*
    Var can be used in lambdas in java 11
*/
import java.util.*;
public class Test {
    public static void main (String[] args) {
        Map<String, String> names = new HashMap<>();
        names.put("David", "Johannes");
        names.put("Idriss", "Görel");
        names.forEach((var key, var value) -> System.out.println(key + " " + value));
    }
}