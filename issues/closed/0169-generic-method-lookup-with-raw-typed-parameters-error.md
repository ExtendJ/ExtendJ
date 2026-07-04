# Generic method lookup with raw typed parameters error

**Status:** resolved

The following piece of code does not compile :
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

## Comments

### Jesper Öqvist - 2016-04-01

I made a test case independent of the Java library:

```java
// .result=COMPILE_PASS
class Container<T> { }

class Comparable<T> { }

class Sorter {
  static <T extends Comparable<? super T>> void sortInPlace(Container<T> c) { }
}

public class Test<A> {
  @SuppressWarnings("unchecked")
  void valueOf(Container in) {
    Sorter.sortInPlace(in);
  }
}
```

This test case is very similar to a test for the issue #168, except that `? super T` is used in the type variable bound. It seems like the fix should be very similar to the fix for issue #168.

### Jesper Öqvist - 2016-04-01

I found a solution for this particular error, but it is not very elegant and it seems like the type argument bounds checking code should be refactored to make it simpler. There is still potentially more errors in this code that needs to be fixed.

I am not interested in refactoring the type bound checking code right now, so I think I will push the current fix anyway and hopefully look at simplifying the code in the future.

### Loïc Girault - 2016-04-01

I'll be happy with any fix you can provide. I'm sorry the recent GenericType refactoring brought this new bugs.

### Jesper Öqvist - 2016-04-01

Improve type wildcard super type bound checking

fixes #169 (bitbucket)


→ <<cset fbb15558565a>>
