// An annotation can have multiple targets
// be used on local variable declarations.
// .result=COMPILE_PASS

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Target;

@Target(value={LOCAL_VARIABLE, TYPE})
@interface MyAnnotation { }

@MyAnnotation public class Test {
  float f() {
    @MyAnnotation float x = 3;
    return x * 2;
  }
}
