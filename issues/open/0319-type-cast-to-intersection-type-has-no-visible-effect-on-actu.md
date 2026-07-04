# Type cast to intersection type has no visible effect on actual type

_ExtendJ 8.1.2-117-g8158643 Java SE 8_

‌

Type cast to intersection type has no visible effect on actual type.

Example:

```java
import java.util.Queue;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

public class Test {
    ArrayList<Integer> l = (AbstractCollection<Integer> & List<Integer> & Queue<Integer>) new ArrayList<Integer>();
}
```

Expected result:

Some form of incompatible type error. Javac generates the following error:

```
error: incompatible types: INT#1 cannot be converted to ArrayList<Integer>

    ArrayList<Integer> l = (AbstractCollection<Integer> & List<Integer> & Queue<Integer>) new ArrayList<Integer>();

                           ^

  where INT#1 is an intersection type:

    INT#1 extends AbstractCollection<Integer>,List<Integer>,Queue<Integer>
```

Actual:

No errors reported, compilation passes.

‌

Likely cause:

in java8/frontend/TypeCheck.jrag is the following code:

```
  syn lazy TypeDecl IntersectionCastExpr.type() = unknownType();
```

‌

## Comments

### Jesper Öqvist - 2023-04-04

My initial impression is that this will require several changes, and might be complicated. Two possible solutions come to mind:

* Add intersection types in the type hierarchy \(as subtype of `TypeDecl`\).
* Compute a type that matches all the types listed in the intersection type and implicitly instantiate it. This type would match the “notional class or interface” mentioned in [JLS 8 4.9](https://docs.oracle.com/javase/specs/jls/se8/html/jls-4.html#jls-4.9)

‌
