# Qualified argument with wildcard-parameterized type rejected in generic method invocation

**Status:** resolved

*ExtendJ 11.0.0-165-g0d14a712 Java SE 8*

A qualified expression of wildcard-parameterized type is
not treated as compatible in method argument position.

```java
public abstract class Test {
  abstract <T> T unc(Class<T> c);
  abstract Class<? extends Test> cls();

  void m(Test o) {
    unc(cls());                          // ok
    Class<? extends Test> c = cls();
    unc(c);                              // ok
    unc((Class<? extends Test>) cls());  // ok
    unc(this.cls());                     // error
    unc(o.getClass());                   // error
    unc(this.getClass());                // error
  }
}
```

Expected result: compilation should succeed.

Actual result: the three qualified-argument calls are rejected:

```
error: no method named unc(java.lang.Class<? extends Test>) in Test matches.
  However, there is a method unc(java.lang.Class<T>)
```
