# Annotation bytecode error

**Status:** resolved

JastAddJ 7.1.1-150-g198ecc4 Java SE 7 generates incorrect bytecode for the following test case:

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Timeout {
            long timeout() default 0L;
    }
    public class Test {
            @Timeout(timeout = 100)
            void m() {
            }
    }

Accessing the timeout value for `m` via reflection raises the following exception:

    Code execution failed when expected to pass:
    Exception in thread "main" java.lang.annotation.AnnotationTypeMismatchException: Incorrectly typed data found for annotation element public abstract long Timeout.timeout() (Found data of type class java.lang.Integer[100])

## Comments

### Jesper Öqvist - 2014-02-27

This error seems to be caused by an integer being placed in the constant pool rather than a long constant value for the annotation element value.

### Jesper Öqvist - 2014-02-27

Fixed bytecode annotation error

Fixed incorrect constant type passed to annotation.

fixes issue #60 (bitbucket)


→ <<cset 02dab289e665>>
