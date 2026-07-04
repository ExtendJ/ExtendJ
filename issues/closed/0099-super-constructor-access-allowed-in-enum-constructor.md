# Super constructor access allowed in enum constructor

**Status:** resolved

**JastAddJ 7.1.1-277-g61a8986 Java SE 7**

Super constructor invocation should not be allowed inside an enum constructor.

Test case:

```
// Enum constructors cannot call super()
// .result=COMPILE_FAIL
enum E {
        ;
        E() {
                super();
        }
}
```

## Comments

### Jesper Öqvist - 2015-02-12

Don't allow super() in enum constructor

fixes #99 (bitbucket)


→ <<cset c0dea6d221fc>>
