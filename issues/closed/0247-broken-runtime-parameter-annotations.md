# Broken runtime parameter annotations

**Status:** resolved

*ExtendJ 8.0.1-248-ga8a2c1e Java SE 8*

ExtendJ generates broken runtime parameter annotations for this test:

```java
import java.lang.annotation.*;

public class Ju7 {

    public void bork(@Trait("borkable") @Kind("spork") int x) {
    }

    public static void main(String[] args) throws NoSuchMethodException {
      Annotation[][] notes = Ju7.class
          .getDeclaredMethod("bork", int.class)
          .getParameterAnnotations();
      System.out.println(notes[0].length);
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
```

Expected result: should print

```
2
@Trait(value=borkable)
@Kind(value=spork)
```

Actual result: fails to run:

```
Exception in thread "main" java.lang.annotation.AnnotationFormatError: Unexpected end of parameter annotations.
        at sun.reflect.annotation.AnnotationParser.parseParameterAnnotations(AnnotationParser.java:163)
        at java.lang.reflect.Executable.parseParameterAnnotations(Executable.java:80)
        at java.lang.reflect.Executable.sharedGetParameterAnnotations(Executable.java:555)
        at java.lang.reflect.Method.getParameterAnnotations(Method.java:639)
        at Ju7.main(Ju7.java:9)
```

## Comments

### Jesper Öqvist - 2017-12-18

Fix error in RuntimeVisibleParameterAnnotations attribute

The number of annotations attached to each parameter was missing in the
RuntimeVisibleParameterAnnotations attribute.

fixes #247 (bitbucket)


→ <<cset fe3a489cb2f8>>
