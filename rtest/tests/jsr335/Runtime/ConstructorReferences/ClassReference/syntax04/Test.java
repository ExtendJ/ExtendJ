// .result: COMPILE_PASS
import java.util.*;

public class Test {

  public interface A {
    List<String> m();
  }

  public static void main(String[] arg) {
    // Tests type variable inferrence (uses diamond internally)
    A a = LinkedList::new;
    List<String> list = a.m();
    list.add("yes");
  }
}
