// Tests a minimal complex annotation, i.e. an annotation type an annotation member.
// See issue 145.
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
