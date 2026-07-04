# Wildcards should not be allowed in parameterized supertype type arguments

*ExtendJ 8.1.1-13-g984cc95 Java SE 8*

ExtendJ allows wildcard types as the type arguments of a supertype.

The Java specification disallows this. See [JLS 8 section 8.1.2](https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.1.2). Specifically, this line:

> If the ClassType has type arguments, it must denote a well-formed parameterized type (§4.5), and none of the type arguments may be wildcard type arguments, or a compile-time error occurs.

Here is a test that fails to give an error with ExtendJ:

```java
public class Test2 extends S<? extends Number> { }
class S<T> {}
```

Expected result: the declarations above should generate a compile error. For example, javac gives this error:

```
javac Test2.java
Test2.java:1: error: unexpected type
public class Test2 extends S<? extends Number> { }
                            ^
  required: class or interface without bounds
  found:    ? extends Number
1 error
```

Actual result: no error was reported for the test.
