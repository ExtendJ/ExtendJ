# Inheriting package private implementation of interface method

**Status:** resolved

**JastAddJ 7.1.1-267-g49411e5 Java SE 7**

Package private methods can not be inherited to implement an interface in a class outside the package. For example:

```
package p1;
public interface I { void m(); }
```

```
package p1;
public class A { void m() {} }
```

```
public class Test extends p1.A implements p1.I {
  // missing implementation of p1.I.m - p1.A.m does not apply
}
```

## Comments

### Jesper Öqvist - 2015-02-11

Stricter interface method implementation check

fixes #91 (bitbucket)


→ <<cset 0f435294b87b>>
