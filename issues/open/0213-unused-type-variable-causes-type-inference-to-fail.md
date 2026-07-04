# Unused type variable causes type inference to fail

*ExtendJ 8.0.1-195-gbd08c51 Java SE 8*

Test case:

```java
public abstract class Test {
  String build() {
    return buildIt();
  }

  abstract <T, U extends T> T buildIt();
}
```

Expected result: should compile without warning

Actual result:

```
    [junit] tests/generics/method_20p/Test.java:4: error: return value must be an instance of java.lang.String which Unknown is not
    [junit] tests/generics/method_20p/Test.java:4: error: no method named buildIt() in Test matches. However, there is a method buildIt()
```

## Comments

### Jesper Öqvist - 2017-11-17

The type inference in ExtendJ is implemented mostly following the specification for Java 5. As far as I know, the Java 7 specification is very similar for the type inference.

The type inference starts by looking at formal arguments. There are none in this case, so it goes on to [15.12.2.8. Inferring Unresolved Type Arguments](https://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.12.2.8).

The initial constraints in this step are:

* `String >> T`
* `T >> U`
* `U << T`

These constraints are simplified to:

* `T <: String`
* `U <: T`
* `T :> U`

Equality constraints are then resolved, but there are none in this case. Then, according to the specification, we should compute `T <: glb(String)` and `U <: glb(T)`. However, the implementation in ExtendJ works differently here and uses `U <: lub(T,U)`.
