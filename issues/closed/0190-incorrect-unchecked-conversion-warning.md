# Incorrect unchecked conversion warning

**Status:** resolved

*ExtendJ 8.0.1-154-gaf5cf06 Java SE 8*

The unchecked conversion analysis in ExtendJ is too strict. The following test case should not generate an unchecked conversion warning:

```java
// Test safe generic type conversion.
// .result=COMPILE_PASS

abstract class AbstractContainer<T> { }
class List<T> extends AbstractContainer<T> { }
class ResourceList extends List<Resource> { }
interface Resource { }

public class Test {
  List<Resource> foo(AbstractContainer con) {
    return ((ResourceList) con);
  }
}
```

ExtendJ generates the following, incorrect, warning:

```
tests/generics/subtype_04p/Test.java:11: warning: unchecked conversion from raw type AbstractContainer to generic type ResourceList
```

## Comments

### Jesper Öqvist - 2017-04-10

Fix incorrect unchecked conversion warning

fixes #190 (bitbucket)


→ <<cset dade8ce023bb>>
