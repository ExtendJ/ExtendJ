// Test that runtime retention is kept when loading an annotation from
// bytecode. The annotation is provided via the runtime library.
// https://bitbucket.org/extendj/extendj/issues/256/meta-annotations-are-dropped-from
// .classpath: @RUNTIME_CLASSES@
import java.lang.reflect.Method;

import runtime.Notes.Timeout;

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

  @Timeout(timeout = 100) static public void m() { }

}

