# Allow type arguments on invocation of non-parameterized method

**Status:** resolved

**JastAddJ 7.1.1-289-g68d98be Java SE 7**

According to the Java language specification it is okay to add type arguments when calling a non-parameterized method:

```java
// According to JLS 15.12.2.1 it is allowed to specify type arguments to non-generic method calls
// .result=COMPILE_PASS
class Test {
        void a() { }
        void b() {
                this.<Test>a();
        }
}
```

JastAddJ currently gives an error for this test case.

JLS references:

* [JLS 3 15.12.2.1](http://docs.oracle.com/javase/specs/jls/se5.0/html/expressions.html#316811)
* [JLS SE7 15.12.2.1](http://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.12.2.1)
* [JLS SE8 15.12.2.1](http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.12.2.1)

## Comments

### Jesper Öqvist - 2015-02-18

Added JLS references.

### Jesper Öqvist - 2015-02-18

Added JLS SE8 reference

### Jesper Öqvist - 2015-02-18

Allow type args on non-generic method call

fixes #104 (bitbucket)


→ <<cset 0c8099600a68>>
