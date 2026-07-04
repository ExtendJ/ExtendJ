# Incorrect duplicate type error

**Status:** resolved

**7.1.1-252-gbd031b3**

When a source compilation unit on the sourcepath has a type with a name not matching the compilation unit, and the same compilation unit is available on the classpath, JastAddJ reports a duplicate type error if the compilation unit is dynamically loaded due to references from other compiled sources.

Test case:

```
// Test.java
import java.net.*;
public class Test {
        URL url;
}

// local file java/net/URL.java
package java.net;
public class URL {
        Parts parts = new Parts();
}
class Parts {
}
```

Error message:

```
    [junit] [FAIL] runTest[type/local_decl_01p](tests.jastaddj.TestJava7)
    [junit] Compilation failed when expected to pass:
    [junit] Errors:
    [junit] tests/type/local_decl_01p/java/net/URL.java:7:
    [junit]   Semantic Error: duplicate type Parts in package java.net
```

## Comments

### Jesper Öqvist - 2015-01-30

Fixed incorrect duplicate type error

Fixed error where a library type could be incorrectly reported as
duplicate declarations when local declarations in source files on the
source path contained the same type. This issue was caused by library
type lookups only working as intended with types having the same name
as the containing compilation unit.

fixes #84 (bitbucket)


→ <<cset f509b79d06a3>>
