# Direct instantiation of inner class without enclosing reference in static inner class

**Status:** resolved

**JastAddJ 7.1.1-271-g22d48cf Java SE 7**

It should not be possible to create an instance of an inner class in a static class without an enclosing class instance.

Test case:

```
// Can not, in a static class, directly instantiate inner class of enclosing type.
// .result=COMPILE_FAIL
class Test {
        static class C {
                D d = new D();// error
        }
        class D {
        }
}
```

## Comments

### Jesper Öqvist - 2015-02-12

Check if enclosing class is static

fixes #95 (bitbucket)


→ <<cset ba3100aa5780>>
