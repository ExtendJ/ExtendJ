# Diamond constructor inference fails if argument is an inferred-type method

*ExtendJ 8.1.0-20-gdf98f7c Java SE 8*

The following test fails to compile:

```java
// With the new type inference in Java 8, a diamond constructor can have
// inferred-type arguments that rely on the target type, including other
// diamond expressions.
// .result: COMPILE_PASS
import java.util.*;
public class Test {
  public static void main(String[] args) {
    Set<String> argSet = new HashSet<>(Collections.emptyList());
  }
}
```

Expected result: should compile

Actual result: fails to compile with this error message:

```
    [junit] [FAIL] runTest[jsr335/diamond/generics_01p](tests.extendj.TestJava7)
    [junit] Compilation failed when expected to pass:
    [junit] tests/jsr335/diamond/generics_01p/Test.java:8: error: can not instantiate java.util.HashSet<java.lang.String> no matching constructor found in java.util.HashSet<java.lang.String>
```
