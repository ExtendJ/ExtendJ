# Improve deprecation warning messages

*ExtendJ 8.0.1-258-g9891139 Java SE 8*

When compiling JUnit 4.12, ExtendJ generates the following warning:

```
src/test/java/org/junit/tests/experimental/rules/TestWatcherTest.java:[85,-1] <init>(java.lang.String) in org.junit.internal.AssumptionViolatedException has been deprecated
```

This warning message could be made more readable by referring to the constructor by the class name instead of `<init>`, and using simple type names instead of the fully qualified type names:

```
src/test/java/org/junit/tests/experimental/rules/TestWatcherTest.java:[85,-1] the constructor AssumptionViolatedException(String) has been deprecated.
```
