# Able to override compareTo in enum declaration

**Status:** resolved

**JastAddJ 7.1.1-278-gc0dea6d Java SE 7**

It is possible to override compareTo in an enum declaration. This should not be allowed.

Test case:

```
// Test overriding final method in enum superclass (java.lang.Enum)
// .result=COMPILE_FAIL
enum Test {
        ;
        public int compareTo(Object other) {
                return 1;
        }
}
```

## Comments

### Jesper Öqvist - 2015-02-12

This problem stems from an issue with override checking where the `Comparable<T>` interface is not taken into account. Since `java.lang.Enum<E extends java.lang.Enum<E>>` implements `Comparable<E>` and the `compareTo(T)` method in `Comparable<E>` have the same erased signature as `Test.compareTo` in the example above, the `Test.compareTo` method cannot implement/override the `Comparable.compareTo` method, and even if it could it would conflict with the `final` declaration of `compareTo` in `java.lang.Enum`.

### Jesper Öqvist - 2015-02-12

Checking of incompatible overrides due to erasure should be improved in JastAddJ, but this is better handled in a separate issue.

### Jesper Öqvist - 2015-02-18

Added issue #103 which describes the root issue causing the above test failure.

### Jesper Öqvist - 2015-02-18

Check for conflicting methods using erasure

Fixed issue making it possible to declare a method with the same erased
signature as an inherited method, yet which did not override that method.

This was fixed by adding an explicit check for inherited methods with
conflicting erased signatures. The fix uses a new attribute
TypeDecl.erasedAnestorMethodsMap.

fixes #103 (bitbucket)
fixes #100 (bitbucket)


→ <<cset 25e77e0b318b>>
