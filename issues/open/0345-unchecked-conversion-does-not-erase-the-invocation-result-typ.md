# Unchecked conversion does not erase the invocation result type

When a raw argument makes unchecked conversion necessary for a generic method
to be applicable, the result type of the invocation is the erasure of the
declared result type (JLS SE8 §18.5.2). ExtendJ instead infers `Object` for the
type arguments, so an assignment that javac accepts as an unchecked assignment
is reported as a type error.

```java
import java.util.List;
public class Test {
  static <U> List<U> f(List<U> l) {
    return l;
  }

  void m(List raw) {
    List<String> l = f(raw); // error
  }
}
```

Expected result: compiles, with an unchecked warning.

Actual result:

```
error: cannot assign variable l of type java.util.List<java.lang.String> a value of type java.util.List<java.lang.Object>
```

The same happens for a diamond class instance creation with a raw argument
(`new HashMap<>(raw)`), and when the type parameter has a bound
(`<U extends Number>`), which javac also accepts because the result type is
erased rather than inferred.

## Where it goes wrong

`BoundSet.rawAccess` records that unchecked conversion was necessary
(§18.5.1). It is set while reducing a subtyping constraint whose left hand side
only has the raw type as a supertype, and it is propagated into an enclosing
bound set by `incorporateBounds`, which is what makes the erasure carry through
a nested invocation.

`GenericMethodDecl.collectBounds` reacts to the flag by skipping the §18.5.2
constraint between the result type and the target type. The inference variables
are left unconstrained rather than uninstantiated, so resolution instantiates
them to `Object` and `MethodAccess.parameterizedDecl` builds a parameterized
method rather than the raw one.

Plain unchecked assignment (`List<String> l = raw`) is accepted, so the problem
is specific to the result type of an invocation.
