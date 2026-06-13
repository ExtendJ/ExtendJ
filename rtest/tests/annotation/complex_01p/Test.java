// Tests a minimal complex annotation, i.e. an annotation type an annotation member.
// See https://bitbucket.org/extendj/extendj/issues/145/incorrect-handling-of-annotation-targets
// .result=COMPILE_PASS
import java.lang.annotation.Target;

@Target({})
@interface Simple {
}

@interface Complex {
  public Simple value();
}

@Complex(@Simple)
public class Test {
}
