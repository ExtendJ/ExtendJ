# Inherited methods with incompatible return types are allowed

**Status:** resolved

Tested with **JastAddJ 7.1.1-258-g867d218 Java SE 8**

Test case:

```
// Incompatible return types in inherited methods
// .result=COMPILE_FAIL
interface I {
    int f();
}
class C {
    public String f() {
        return "x";
    }
}
abstract class Test extends C implements I {
}
```

Expected output:

```
Test.java:13: error: f() in C cannot implement f() in I
abstract class Test extends C implements I {
         ^
return type String is not compatible with int
1 error
```

## Comments

### Jesper Öqvist - 2015-02-08

Added incompatible inherited methods check

Check for incompatible inherited methods from superclass and interfaces.

fixes #87 (bitbucket)


→ <<cset 1cc8abc75c88>>
