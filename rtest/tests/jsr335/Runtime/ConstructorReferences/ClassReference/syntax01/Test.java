// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;
import java.util.*;

public class Test {
  public interface A {
    Integer m(int i);
  }

  public static void main(String[] arg) {
    A a = Integer::new;
    Integer i = a.m(20);
    testTrue("ConstructorReference", i.intValue() == 20);
    testTrue("ConstructorReference", i instanceof Integer);
    testTrue("ConstructorReference", i.equals(Integer.valueOf(20)));
  }
}
