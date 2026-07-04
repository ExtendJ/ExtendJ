# Generic method lookup error

**Status:** resolved

**ExtendJ 8.0.1-115-ge711456**

There is an error in the method lookup in ExtendJ which causes it to discard a matching generic method when using certain types of type variable bounds and a raw formal argument type. This test case exposes the bug:

```java
// Test accessing a parameterized static method using a raw parameter value.
// .result=COMPILE_PASS
class Container<T> {
  T value;
}

class Foo<T> { }

class Helper {
  static <T extends Foo<T>> T valueOf(Container<T> c) {
    return c.value;
  }
}

public class Test {
  @SuppressWarnings("unchecked")
  Object valueOf(Object in) {
    return Helper.valueOf((Container) in);
  }
}
```

The `Helper.valueOf((Container) in)` call gives the following error message from ExtendJ:

```
Test.java:18: error: no method named valueOf(Container) in Helper matches. However, there is a method valueOf(Container<T>)
```

The error goes away if the signature of the `Helper.valueOf` method is changed to the following:

```java
static <T> T valueOf(Container<T> c) {...
```

## Comments

### Jesper Öqvist - 2016-04-01

Improve type variable type bound checking

fixes #168 (bitbucket)


→ <<cset 895024e7f400>>

### Jesper Öqvist - 2016-04-01

The fix introduced another error.

### Loïc Girault - 2016-04-01

I was about to submit an issue about generic method lookup that does not work with raw types.
Seems related to this issue so I just post you the sample of code that does not work:

```
#!java
package p;

import java.util.Vector;
import java.util.Collections;

class C {
    void m(){
        Vector v = new Vector();
        Collections.sort(v);
    }
}
```

### Jesper Öqvist - 2016-04-01

Improve type variable type bound checking

fixes #168 (bitbucket)


→ <<cset d41726f1b827>>

### Loïc Girault - 2016-04-01

The sample of code I send you is still not compiling. Should I open another issue ?

### Jesper Öqvist - 2016-04-01

Yes please!

### Loïc Girault - 2016-04-01

Done ! ^_^
