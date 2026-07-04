# Object is not compatible with type variable extending Object

**Status:** resolved

**JastAddJ 7.1.1-289-g68d98be Java SE 7**

The type Object is not compatible with T extends Object. JastAddJ currently allows unchecked conversion from Object to T. See the test case below for an example:

```java
// .result=COMPILE_FAIL
class Test<T> {
    T _() {
        return new Object();
    }
}
```

## Comments

### Jesper Öqvist - 2015-02-19

The problem here is that the type analysis rules for type variables allow a type which is a subtype to the upper bound of a type variable to be assignment compatible to that type variable. However, this rule seems to be intended for type checking of type arguments, not assignments to things that have an upper bound type.

### Jesper Öqvist - 2015-02-19

According to [JLS SE7 4.10.2](http://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.10.2), it seems that a type variable with no lower bound can not be supertype to any other type.

We should have a special mechanism to check capture conversion of type arguments instead of using the subtype attribute.

### Jesper Öqvist - 2015-03-31

Refactored type argument bounds checking

Added attributes to explicitly check type bounds (withinBounds).  This fixes
problems causing some incompatible types to not be detected by the type
checking attributes.

fixes #105 (bitbucket)


→ <<cset 06b345f1f759>>

### Jesper Öqvist - 2015-04-14

typo
