# Inheriting concrete methods with equal signature due to generics

**Status:** resolved

**JastAddJ 7.1.1-284-gc3ca38b Java SE 7**

Inheriting two concrete methods with the same signature, due to type argument substitution, should not be allowed.

According to [JLS SE7 8.4.8.4](http://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#jls-8.4.8.1):

> It is a compile-time error if a class C inherits a concrete method whose signature is a subsignature of another concrete method inherited by C. This can happen if a superclass is generic, and it has two methods that were distinct in the generic declaration, but have the same signature in the particular invocation used.

Test case:

```java
// It is an error if an concrete inherited method has same signature to another concrete inherited method (JLS SE7 8.4.8.4)
// .result=COMPILE_FAIL
class A<U> {
        void m(U u) { }
        void m(Number _) { }
}

class Test extends A<Number> {
}
```

## Comments

### Jesper Öqvist - 2015-02-16

Improved checking of duplicate inherited methods

Check for duplicate inherited methods due to type parameterization.

fixes #102 (bitbucket)


→ <<cset 79c1ae6f944f>>

### Jesper Öqvist - 2015-02-17

Improved checking of duplicate inherited methods

Check for duplicate inherited methods due to type parameterization.

fixes #102 (bitbucket)


→ <<cset 33094e84d2f9>>
