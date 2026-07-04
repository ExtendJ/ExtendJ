# Array types in bounds

**Status:** resolved

[JLS version 7, Chapter 4.4](http://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.4) states:

     Every type variable declared as a type parameter has a bound. If no bound is declared for a type variable, Object is assumed. If a bound is declared, it consists of either:
     *  a single type variable T, or
     *  a class or interface type T possibly followed by interface types I1 & ... & In.

JastAddJ currently does not give an error if an array type is used as bound. For example, the following declaration should be invalid because of the array-type in the bound for T:

```
#!java

interface X {
    <T extends ArrayList<T>[]> T execute(T t);
 }
```

## Comments

### Jesper Öqvist - 2015-04-16

There is a check to test that the first type bound is either a type variable, class type, or interface type. This check also allowed array types because it uses the `isClassDecl` attribute which evaluated to true for `ArrayDecl`. The simple fix is to add an equation for `ArrayDecl` so that it is not considered a class type. After testing this change it does not appear that anything else depends on ArrayDecls returning true for `isClassDecl`.

### Jesper Öqvist - 2015-04-16

ArrayDecl.isClassDecl is now false

The ArrayDecl.isClassDecl attribute now returns false. This fixes the issue
that array types were allowed as type variable bounds.

fixes #63 (bitbucket)


→ <<cset b3bf40c03a52>>
