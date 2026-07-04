# Type inference error for enhanced-for loop with inferred-type expression

**Status:** resolved

*ExtendJ 8.1.0-45-g5c1c58b Java SE 7*

```java
// Test that enhanced-for can iterate over a generic type created with diamond.
// .result: COMPILE_PASS
import java.util.*;
public class Test {
  void test(Collection<String> strs) {
    for (String s : new HashSet<>(strs)) {
      System.out.println(s);
    }
  }
}
```

Expected result: should compile without error.

Actual result: compilation error:

```
    [junit] Compilation failed when expected to pass:
    [junit] tests/jsr334/diamond/enhanced_for_01p/Test.java:6: error: parameter of type java.lang.String can not be assigned an element of type java.lang.Object
```

Additional test without diamond access:

```
import java.util.*;
public class Test {
  void test() {
    for (String s : Collections.emptyList()) {
      System.out.println(s);
    }
  }
}
```

## Comments

### Jesper Öqvist - 2018-01-11

Improve type inference in enhanced-for expression

fixes #285 (bitbucket)


→ <<cset 4261255e438c>>

### Jesper Öqvist - 2026-06-20

The issue description appears to be wrong. The program is not allowed in Java 8 or Java 21. I have not tested Java 5.

For example, here is the Java 21 compile error:



```
tests/generics/enhanced_for_04p/Test.java:7: error: incompatible types: Object cannot be converted to String
    for (String s : Collections.emptyList()) {
                                         ^
1 error
```

### Jesper Öqvist - 2026-06-21

Replace assignConvertedType with targetType

This commit backports the `targetType` and `assignmentContext`
attributes from the java8 module to the java5 module. The
`assignConvertedType` was initially only for generic method inference in
java5, but this purpose can be accomplished with `targetType` instead
and this simplifies the overall attribute system for java8 forward.

This commit updates the java5 generic method inference
(`inferTypeArguments`) to use a new attribute
`Expr.targetTypeInference` to decide when `targetType` is used for the
inference. In java5, `targetTypeInference` is
synonymous with `assignmentContext` and in java8 it is refined to
`assignmentContext || invocationContext`. This matches the Java
specification better and avoids coding in the rules for when
`targetType` is used for inference inside the `targetType` attribute.

As part of applying this refactoring I discovered that generic
method inference for the enhanced for loop variable was incorrect (see
issue #285). The refactoring incidentally fixed this incorrect
inference because enhanced for loop variables were previously treated as
if they were in assignment context.

fixes #285 and #343


→ <<cset 4a17363c6e80>>
