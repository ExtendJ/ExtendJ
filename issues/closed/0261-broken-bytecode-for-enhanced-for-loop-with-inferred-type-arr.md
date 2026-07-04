# Broken bytecode for enhanced-for loop with inferred type array expression

**Status:** resolved

*ExtendJ 8.1.0-4-ge296850 Java SE 7*

The following generates broken bytecode:

```java
import java.util.Set;
import java.util.Collections;

public class An3 {
  public static void main(String[] args) {
    foo(Collections.singleton("x marks the spot"));
  }

  static void foo(Set<String> set) {
    for (String msg : set.toArray(new String[set.size()])) {
      System.out.println(msg);
    }
  }
}
```

Expected result: the program should print `x marks the spot`

Actual result: the compiled program fails to run with this error:

```
Exception in thread "main" java.lang.VerifyError: Instruction type does not match stack map
Exception Details:
  Location:
    An3.foo(Ljava/util/Set;)V @21: iload_2
  Reason:
    Type '[Ljava/lang/Object;' (current frame, locals[1]) is not assignable to '[Ljava/lang/String;' (stack map, locals[1])
...
```

## Comments

### Jesper Öqvist - 2018-01-02

It seems like the problem is a missing `CHECKCAST` to convert the erased result of `toArray` to `[Ljava/lang/String;`.

### Jesper Öqvist - 2018-01-03

Fix enhanced-for code generation issue

Fixed problem in casting conversions when using array types in enhanced for
statements.

fixes #261 (bitbucket)


→ <<cset eed723d1da7b>>
