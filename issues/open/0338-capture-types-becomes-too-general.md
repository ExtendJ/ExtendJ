# Capture types becomes too general

_ExtendJ 8.1.2-117-g8158643 Java SE 8_

When initializing a parameterized type containing a wildcard, the capture type becomes too general.

Example:

```java
import java.util.*;
public class Test {
    public static void main (String[] args) {
        List<String> l1 = new ArrayList<>();
        l1.add("Hello");
        List<?> l2 = l1;

        Object x = l2.get(0);
        l2.add(x); // error
    }
}
```

Expected result:

Some form of type mismatch. Javac generates the following error:

```
Test.java:9: error: no suitable method found for add(Object)
        l2.add(x); // error
          ^
    method Collection.add(CAP#1) is not applicable
      (argument mismatch; Object cannot be converted to CAP#1)
    method List.add(CAP#1) is not applicable
      (argument mismatch; Object cannot be converted to CAP#1)
    method List.add(int,CAP#1) is not applicable
      (actual and formal argument lists differ in length)
  where CAP#1 is a fresh type-variable:
    CAP#1 extends Object from capture of ?
1 error
```

Actual result: no errors reported, compilation passes.

The parameter type for add\(\) seems to be just a type varaible.
