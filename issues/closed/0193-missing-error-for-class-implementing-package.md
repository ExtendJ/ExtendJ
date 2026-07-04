# Missing error for class implementing package

**Status:** resolved

*ExtendJ 8.0.1-161-g2081568 Java SE 8*

A class may not implement a package, however the following test case gives no error when compiled with ExtendJ:

```java
// Package name used as a typename.
// A class may not implement a package.
// .result: COMPILE_FAIL
package org.extendj;

public class Test implements org.extendj {
}
```

## Comments

### Jesper Öqvist - 2017-04-24

Report errors for uses of unknown qualified types

Improved error checking for type accesses to handle unknown qualified types.

Adjusted the error messages for the inaccessible type error and the ambiguous
type error.

fixes #192 (bitbucket)
fixes #193 (bitbucket)
fixes #194 (bitbucket)
fixes #195 (bitbucket)
fixes #196 (bitbucket)


→ <<cset 53debda90fb3>>
