# Static import-on-demand with wrong qualified name of inner class

**Status:** resolved

**JastAddJ 7.1.1-313-gbcfa5f1 Java SE 7**

JastAddJ allows a static import-on-demand declaration with an incorrect qualified name referring to an inner class.

Test case:

```java
import static pkg.B.Inner.*;
class Test {}

/// package "pkg":
public class A {
        public static class Inner {}
}
public class B extends A {
}
```

The above test should fail to compile because `pkg.B.Inner` is not the canonical name of `pkg.A.Inner`.

## Comments

### Jesper Öqvist - 2015-04-25

`StaticImportOnDemandDecl` should have a name check method similar to that for `TypeImportOnDemandDecl`:

```java
  public void TypeImportOnDemandDecl.nameCheck() {
    if(getAccess().lastAccess().isTypeAccess() && !getAccess().type().typeName().equals(typeName()))
      error("On demand type import " + typeName() + ".* is not the canonical name of type " + getAccess().type().typeName());
  }
```

A similar error-checking method exists for `SingleStaticImportDecl` in `java5/frontend/StaticImports.jrag`.

### Jesper Öqvist - 2015-04-25

Regression test: `pkg/import_04f`

### Jesper Öqvist - 2015-04-25

Added static import on demand name checking

fixes #111 (bitbucket)


→ <<cset 3a38bb044615>>
