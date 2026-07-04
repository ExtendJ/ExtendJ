# Should not be able to use default constructor of protected class in external package

**Status:** resolved

**JastAddJ 7.1.1-270-g45f31b2 Java SE 7**

Using of a protected class default constructor in a class outside the containing package should not work:

```
// Test using protected inner class outside this package.
// It works if we extend the outer class, but we can not access
// the constructor.
// .result=COMPILE_FAIL
class Test extends p1.A {
        p1.A.X x = new p1.A.X();
}
```

```
package p1;
public class A {
        protected class X {
        }
}
```

## Comments

### Jesper Öqvist - 2015-02-11

According to [JLS SE7 6.6.2.2](http://docs.oracle.com/javase/specs/jls/se7/html/jls-6.html#jls-6.6.2.2) a qualified access to a protected constructor is allowed outside the package only if it is in the context of an anonymous class instance expression, or a super constructor invocation. This complicates the access rules a bit in JastAddJ since now the rule is simply that a constructor can be accessed from an type if the enclosing type of the access has access to the host type of the constructor and the constructor is protected or public.

### Jesper Öqvist - 2015-02-11

Since the constructor lookup relies on the `ConstructorDecl.accessibleFrom(TypeDecl)` attribute to find applicable constructors even for anonymous class instance expressions that attribute should not be changed - that would mean anonymous class instance expressions stop working according to the spec. Instead we need to check each constructor access separately from the `lookupConstructor` attribute.

### Jesper Öqvist - 2015-02-12

Add extra constructor accessibility check

fixes #94 (bitbucket)


→ <<cset 4efd3fd909b9>>
