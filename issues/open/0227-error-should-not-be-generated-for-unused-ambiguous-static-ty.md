# Error should not be generated for unused ambiguous static type imports

*ExtendJ 8.0.1-222-g9a85c80 Java SE 8*

Ambiguous static imports are fine if they are not used. ExtendJ differs from javac in that it always reports an error for ambiguous static imports.

```java
// Conflicting single-static imports are OK if not used.
// .result: COMPILE_PASS
import static alfa.Alfa.Gamma;
import static beta.Beta.Gamma;
public class Test {
}
```

Expected result: no compile error.

Actual result: an error is reported for the ambiguous import:

```
    [junit] [FAIL] runTest[pkg/static_import_03p](tests.extendj.TestJava7)
    [junit] Compilation failed when expected to pass:
    [junit] tests/pkg/static_import_03p/Test.java:3: error: Gamma is imported multiple times
    [junit] tests/pkg/static_import_03p/Test.java:4: error: Gamma is imported multiple times
```
