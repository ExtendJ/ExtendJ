// Test runtime retention of parameter annotations.
// https://bitbucket.org/extendj/extendj/issues/247/broken-runtime-parameter-annotations
import java.lang.annotation.*;

public class Test {
    public void bork(@Trait("borkable") @Kind("spork") int x) {
    }

    public static void main(String[] args) throws NoSuchMethodException {
      Annotation[][] notes = Test.class
          .getDeclaredMethod("bork", int.class)
          .getParameterAnnotations();
      System.out.println(notes[0][0]);
      System.out.println(notes[0][1]);
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@interface Trait {
  String value() default "";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@interface Kind {
  String value() default "";
}
