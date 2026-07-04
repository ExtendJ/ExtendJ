# Declaring a method with the same erasure as an inherited method, yet not overriding it, should give an error

**Status:** resolved

**JastAddJ 7.1.1-285-g33094e8 Java SE 7**

It is now possible to have a method declared with the same erased signature as an inherited method even when it does not override the method due to type parameterization.

Test case:

```java
// The method compareTo in Test does not override compareTo in Comparable<T>, with T=Test, but has the same erased signature.
// .result=COMPILE_FAIL
class Test extends C<Test> {
        public int compareTo(Object o) {
                return 0;
        }
}

class C<T extends C> implements Comparable<T> {
        public int compareTo(T o) {
                return 1;
        }
}
```

## Comments

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
