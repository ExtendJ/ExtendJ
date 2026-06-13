// https://bitbucket.org/extendj/extendj/issues/222/lambda-expression-causes-unused-class-to
// .classpath: @RUNTIME_CLASSES@
import java.util.*;
import static runtime.Test.*;

public class Test {
  
  public int field = 3;

  public interface A {
    public B m(int i);
  }
  
  public interface B {
    public boolean m();
  }
  
  public A getA() {
    return field -> () -> {
      return this.field == field;
    };
  }
  
  public static void main(String[] arg) {
    Test t = new Test();
    A a = t.getA();
    testTrue(a.m(3).m());
    testFalse(a.m(4).m());
  }
}
