# Missing capture conversion in the java5 module

**Status:** open

*ExtendJ 11.0.0-159-gabcd7d63 Java SE 5*

The `java5` module does not implement capture conversion.
Instead, unbounded wildcard type arguments are substituted by `java.lang.Object`
in member signatures of the parameterized (substituted) type declarations.

```java
class Aelita<T> {
  void add(T t) { }
}

class Test {
  void m(Aelita<?> l) {
    l.add(new Object());
  }
}
```

Expected result: compile error. Nothing except `null` is assignable to the
capture of `?`.

Actual result: compiles without error.

The erasure of `?` is `java.lang.Object`, so any argument is accepted. This
leads to possible heap-pollution.

The bounded variant is correctly rejected, because a bounded wildcard survives
as the parameter type instead of being erased:

```java
void m(Aelita<? extends Number> l) { l.add(Integer.valueOf(1)); }
```

The `java8` module rejects this example with:

```
error: no method named add(java.lang.Object) in Aelita<?> matches.
  However, there is a method add(capture#1 of ?)
```
