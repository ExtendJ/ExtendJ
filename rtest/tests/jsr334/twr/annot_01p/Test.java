// Test that annotation with Target=LOCAL_VARIABLE can be used on a resource
// declaration.
// https://bitbucket.org/extendj/extendj/issues/315/annotations-with-target-local_variable-are
// .result=COMPILE_PASS
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Target;

public class Test {
  interface Resource extends AutoCloseable { }
  interface ResourceBuilder {
    Resource build();
  }
  void test(ResourceBuilder builder) {
    try (@Annot Resource r1 = builder.build()) { } catch (Exception e) {}
  }
}

@Target(LOCAL_VARIABLE)
@interface Annot {
}

