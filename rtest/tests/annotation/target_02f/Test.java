// An annotation type using the meta-annotation Target(LOCAL_VARIABLE) can only
// be used on local variable declarations.
// .result=COMPILE_FAIL

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class Test {
    @Target(ElementType.LOCAL_VARIABLE)
    @interface MyAnnotation {
    }

    @MyAnnotation float x; // Field x is not a valid target of MyAnnotation.
}
