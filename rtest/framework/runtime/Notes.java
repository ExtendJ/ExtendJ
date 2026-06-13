package runtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// This class has some annotation types that will be be parsed from bytecode
// and used in some tests.
public class Notes {

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  public @interface Timeout {
	  long timeout() default 0L;
  }

}
