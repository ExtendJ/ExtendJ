// An annotation type using the meta-annotation Target(LOCAL_VARIABLE) can only
// be used on local variable declarations.
// .result=COMPILE_PASS

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class Test {
    @Target(ElementType.LOCAL_VARIABLE)
    @interface MyAnnotation {
    }

    float f() {
      @MyAnnotation float x;
      x = 3;
      return x * 2;
    }
}
