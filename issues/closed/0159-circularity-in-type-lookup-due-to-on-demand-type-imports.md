# Circularity in type lookup due to on-demand type imports

**Status:** resolved

**ExtendJ 8.0.1-101-gf92bc19**

There is a circular dependency between attributes in the type lookup in ExtendJ causing a stack overflow exception because there is a lack of a circular attribute. The bug may be a bit difficult to understand, but the following test case exposes the problem. The test case consists of several Java files:

**Test.java:**

```java
// Test a circular import-on-demand dependency.
// The implements clause in pkg/C.java causes type lookup to load the on-demand
// import pkg.D.*, this in turn leads to evaluating the member types of pkg.D,
// which requires loading the on-demand imported types for the import
// declarations in pkg/D.java.  Since the import pkg.C.* is before the import
// pkg.E.* which provides the type X we are looking for, the member type sof
// pkg.C are evaluated first, but leads back to the original on-demand import
// lookup.
// .result=COMPILE_PASS
public class Test extends pkg.C {
}
```

**pkg/C.java:**

```java
package pkg;

import pkg.D.*;

public class C implements D.I {
}
```

**pkg/D.java:**

```java
package pkg;

import pkg.C.*;
import pkg.E.*;

public class D implements X {
}
```

**pkg/E.java:**

```java
package pkg;

public interface E {
  public interface X {
    public interface I {
    }
  }
}
```

The above test case causes a stack overflow in ExtendJ.

## Comments

### Jesper Öqvist - 2016-04-01

Fix circularity in on-demand import resolution

Type lookup is circularly defined for on-demand import declarations.  Replaced
treeCopy() calls with treeCopyNoTransform() to avoid type lookup, via ParseName
rewriting, during parameterized type construction.

fixes #159 (bitbucket)


→ <<cset c9a97b7cea1b>>
