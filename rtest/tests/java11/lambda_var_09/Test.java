// .result=EXEC_PASS

/*
    Var can be used in lambdas in java 11
*/
import java.util.*;
public class Test {
    public static void main (String[] args) {
        Map<Integer, List<String>> map = new HashMap<>();
        map.put(1, Arrays.asList("Apple", "Ant"));
        map.put(2, Arrays.asList("Ball", "Bat"));
        map.put(3, Arrays.asList("Cat", "Car", "Cup"));
        map.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach((var entry) -> {
                int key = entry.getKey();
                List<String> values = entry.getValue();
                System.out.println("Key: " + key);
                System.out.println("Values: " + values);
   });

    }
}