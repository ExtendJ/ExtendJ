// Test generic standard library methods: Arrays.asList(T...) and ArrayList.toArray(T[]).
import java.util.*;
@SuppressWarnings("unchecked")
public class Test {
  public static void main(String[] args) {
    String[] names = { "bort", "bart" };
    ArrayList list = new ArrayList(Arrays.asList(names));
    String[] result = (String[]) list.toArray(new String[list.size()]);
    if (result.length != names.length) {
      System.err.println("Length mismatch.");
    }
    for (int i = 0; i < result.length; ++i) {
      if (result[i] != names[i]) {
        System.out.println("Element reference has changed.");
      }
    }
  }
}
