# Incorrect handling of annotation targets

**Status:** resolved

**ExtendJ 8.0.1-72-g08dcc1d**

Annotation types can be annotated with the [java.lang.annotation.Target](https://docs.oracle.com/javase/7/docs/api/java/lang/annotation/Target.html) annotation to determine which types of declarations the annotation can be used in. If the list of element types in the Target annotation is empty, then the annotation may only be used as a member type in complex annotations. A simple example of a complex annotation:

```java
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
```

The above test code is correct, but ExtendJ rejects it because it decides that the `@Simple` annotation cannot be used anywhere, not even inside the `@Complex` annotation. This goes against the Java language specification

## Comments

### Jesper Öqvist - 2016-03-01

Allow all annotations inside complex annotations

Fixed error causing ExtendJ to incorrectly complain about annotations that
should be allowed in complex annotations.

fixes #145 (bitbucket)


→ <<cset 1779b4322919>>
