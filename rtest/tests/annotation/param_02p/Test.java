// Test multiple runtime parameter annotations.
// https://bitbucket.org/extendj/extendj/issues/247/broken-runtime-parameter-annotations
import java.lang.annotation.*;

public class Test {
    public void bork(
        @Trait("crumbly") @Kind("towel") int x,
        @Kind("telephone") int y,
        int z,
        @Trait("bouncing") boolean w
        ) {
    }

    public static void main(String[] args) throws NoSuchMethodException {
      Annotation[][] notes = Test.class
          .getDeclaredMethod("bork", int.class, int.class, int.class, boolean.class)
          .getParameterAnnotations();
      for (int i = 0; i < notes.length; ++i) {
        System.out.println("Parameter " + i);
        for (int j = 0; j < notes[i].length; ++j) {
          System.out.println("  " + notes[i][j]);
        }
      }
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
