# Improve type inference failure reporting

When type inference fails in ExtendJ, either due to an untypable expression or because ExtendJ can't find the correct type arguments, ExtendJ assigns the Unknown type to all type arguments that it fails to infer a type for. This later leads to a typing error or method lookup error.

However,  a better approach would be to print a message about conflicting or insufficient typing constraints, similar to how javac handles type inference failures.

An example of a type inference error from javac:

```
Test.java:3: error: incompatible types: inferred type does not conform to equality constraint(s)
  S<A> n = f(g());
            ^
    inferred: A
    equality constraints(s): A,CAP#1
  where CAP#1 is a fresh type-variable:
    CAP#1 extends Object super: B from capture of ? super B
1 error
```

The error reported by ExtendJ 8.1.1 for the same test:

```
Test.java:3: error: no method named f(S<? super B>) in Test matches. However, there is a method f(S<x>)
```

The "no method matches" error is very general and can be reported because a number of reasons. If a more specific error message about the type inference failure were reported, it would be easier for users to deduce how to fix the error.
