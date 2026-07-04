# NullPointerException in type inference when target type uses a type variable and the inference expression is in a field initializer

**Status:** resolved

*ExtendJ 8.0.1-195-gbd08c51 Java SE 8*

There is a NullPointerException that can be thrown in type inference, caused by a test case like this:

```java
// .result: COMPILE_PASS
import java.util.Collections;
import java.util.List;

public class Test<E> {
  List<E> list = Collections.emptyList();
}
```

It is caused by the nullable attribute `enclosingBlock()` used without null test in `Expr.inferTypeArguments()`:

```java
        TypeVariable v = (TypeVariable) type;
        if (enclosingBlock().lookupType(v.name()) != v) {
          // This type variable is not defined in outer scope: replace it with its bound.
          type = v.firstBound();
        }
```

These guilty commit is dc90ff53d273b23bb66186828c4abab77dd0c25d

## Comments

### Jesper Öqvist - 2017-11-16

Reverting the commit that introduced the bug causes the following test to fail: `jsr335/consref/type_inf_01p`

```java
// Calculating the type of a constructor reference in a type
// inference context.
// See https://bitbucket.org/extendj/extendj/issues/180/method-reference-stack-overflow-error
// .result=COMPILE_PASS

public class Test {
  <T> T build(Builder<T> builder) {
    return builder.build();
  }

  void m() {
    build(Test::new);
  }
}

interface Builder <T> {
  T build();
}
```

### Jesper Öqvist - 2017-11-16

It is pretty simple to fix the NullPointerException, but it would be better if I could find a Java 5 test case that needs the changes added in commit dc90ff53d273b23bb66186828c4abab77dd0c25d.

That can be a task for later. Will commit the fix now.

### Jesper Öqvist - 2017-11-16

Fix NullPointerException in type inference

Fixed an error in type inference that caused NullPointerException when a type
variable was used in a generic type inference context inside a field
declaration initializer expression.

fixes #212 (bitbucket)


→ <<cset b87b260dd3dc>>
