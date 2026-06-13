// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

import java.util.*;

/* Tests that a diamond poly expression can help
 * make the obvious choice during overload resolution
 */

public class Test {
    public static void m(Integer i) { System.out.println("1"); }
    public static void m(ArrayList<Integer> i) { System.out.println("2"); }

    public static void main(String[] arg) {
        m(new ArrayList<>());
    }
}
