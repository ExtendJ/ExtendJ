# Unhandled exception throwing not reported when in anonymous class method

**Status:** resolved

**JastAddJ 7.1.1-303-g42f3ad6 Java SE 7**

JastAddJ does not report an error when throwing an uncaught checked exception in a method if the method is inside an anonymous class instantiated inside a method that handles the exception (or inside another construct that handles it, like a try-statement).

Test case:

```java
// Check that uncaught exception is reported in anonymous class declaration
// .result=COMPILE_FAIL
class Test {
        void m() throws Exception {
                new Test() {
                        void m() {
                                throw new Exception("not declared thrown");// error
                        }
                }.m();
        }
}
```

## Comments

### Jesper Öqvist - 2015-04-14

Improved exception checking

* TypeDecl.isCheckedException() now returns true if the type is a subtype of
java.lang.RuntimeException or java.lang.Error.

* TypeDecl.isUncheckedException() is now exactly the inverse of
TypeDecl.isCheckedException().

* Exception handling error checking has been sped up by checking first if an
exception type is a checked exception, rather than checking this as the last
step of the inherited handlesException attribute.

fixes #106 (bitbucket)


→ <<cset 95c833a11f04>>
