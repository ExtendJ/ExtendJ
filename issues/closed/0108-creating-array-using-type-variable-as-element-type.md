# Creating array using type variable as element type

**Status:** resolved

It should not be allowed to create a new array with a type variable element type, for example:

```
public class Test<T extends Integer> {
        T[] array = new T[10];// error: not valid array element type
}
```

This error is specified in [JLS SE7 $15.10](https://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.10):

>It is a compile-time error if the ClassOrInterfaceType does not denote a reifiable type ($4.7).

## Comments

### Jesper Öqvist - 2015-04-16

We don't have an attribute for checking if a type is reifiable. An `isReifiable` attribute would be useful.

### Jesper Öqvist - 2015-04-16

An `isReifiable` attribute already existed, but it was in the Java 7 extension. It should be moved to the Java 5 extension so that it can be used for array creation type checking.

### Jesper Öqvist - 2015-04-16

Type check generic array creation

Disallow creating arrays with non-reifiable element type.

Moved TypeDecl.isReifiable from Java 7 extension to Java 5 extension.

fixes #108 (bitbucket)


→ <<cset 3a0d0fa66387>>
