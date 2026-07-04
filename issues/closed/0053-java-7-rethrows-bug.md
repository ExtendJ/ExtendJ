# Java 7 rethrows bug

**Status:** resolved

[JLS version 7, Chapter 14.20](http://docs.oracle.com/javase/specs/jls/se7/html/jls-14.html#jls-14.20) states:

     In a uni-catch clause, an exception parameter that is not declared final (implicitly or explicitly) is considered effectively final if it never occurs within its scope as the left-hand operand of an assignment operator

The modifiedInScope-attribute used to compute effectively final does not traverse subexpressions and thus not catch assignment expressions that are located inside other expressions.

## Comments

### Erik Hogeman - 2014-02-24

Test case for this bug:


```
#!java

public static void main(String[] args) {
	try {

	} catch(Exception e) {
		Exception e2 = e = new Exception();
		throw e; //Should fail, not effectively final here
	}
}
```

### Jesper Öqvist - 2014-02-24

The test you suggest is incorrect. The thrown exception is not caught or declared thrown, and that is the only reason the test will not compile (in Java 7). The effectively finalness of e has no impact in the suggested test.

Added the following tests which cover this bug:

    jsr334/more-precise-rethrow/effectively_final_01f
    jsr334/more-precise-rethrow/effectively_final_01p
    jsr334/more-precise-rethrow/effectively_final_02f
    jsr334/more-precise-rethrow/effectively_final_02p
    jsr334/more-precise-rethrow/effectively_final_03f
    jsr334/more-precise-rethrow/effectively_final_03p
    jsr334/more-precise-rethrow/effectively_final_04f
    jsr334/more-precise-rethrow/effectively_final_05f
    jsr334/more-precise-rethrow/effectively_final_06f
    jsr334/more-precise-rethrow/effectively_final_07f
    jsr334/more-precise-rethrow/effectively_final_08f
    jsr334/more-precise-rethrow/effectively_final_09f
    jsr334/more-precise-rethrow/effectively_final_10f
    jsr334/more-precise-rethrow/effectively_final_11f
    jsr334/more-precise-rethrow/effectively_final_12f
    jsr334/more-precise-rethrow/effectively_final_13f

Example of correct test:

    class Test {
            class E1 extends Exception { }
            void m() throws E1 {
                    try {
                            u();
                    } catch (Exception e) {
                            // statement modifying e:
                            (e = new E1()).getMessage();
                            // e no longer effectively final so we can not assume
                            // that e is instanceof E1 according to Java 7 specs
                            throw e;
                    }
            }
            void u() throws E1 {
                    throw new E1();
            }
    }

In this test we have an uncaught/unhandled exception of type `Exception` because e is not effectively final. Make `e` effectively final by removing the assignment to `e` and the test case compiles.

### Jesper Öqvist - 2014-02-24

Fixed effectively final errors

fixes issue #53 (bitbucket)


→ <<cset a851d2162992>>

### Erik Hogeman - 2014-02-24

Sorry about that, thanks for pointing it out.
