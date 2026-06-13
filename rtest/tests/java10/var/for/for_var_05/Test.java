// .result=EXEC_PASS

/*
 * Index variables declared in enhanced for loops
 */
import java.util.*;
public class Test {
    public static void main(String[] args) {
        var map = new HashMap<String, Object>();
        map.put("a", "b");
        map.put("b", 1);
        map.put("c", 1.0);

        for (var entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
