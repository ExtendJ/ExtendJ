// Test runtime visible method annotation.
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Timeout {
  long timeout() default 0L;
}

public class Test {
  public static void main(String[] args) {
    Test test = new Test();
    Class klass = Test.class;
    for (Method method: klass.getDeclaredMethods()) {
      Timeout annotation = method.getAnnotation(Timeout.class);
      if (annotation != null) {
        long timeout = annotation.timeout();
        if (timeout != 100L) {
          throw new Error("unexpected timeout!");
        }
        return;
      }
    }
    throw new Error("could not find annotated method!");
  }

  @Timeout(timeout = 100) void m() { }
}
