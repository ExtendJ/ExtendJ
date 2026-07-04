# Incorrect type signatures for inner classes

**Status:** resolved

*ExtendJ 8.1.0-57-g2a09f5f Java SE 8*

When ExtendJ generates type signatures including inner classes, the inner classes are referred to by the source type name, e.g., `X.Y`. The actual bytecode class name should be used instead: `X$Y`.

This causes compile problems in JaCop 4.0.0 when the scala sources are compiled:

```
warning: Class org.jacop.constraints.GCC.Component not found - continuing with a stub.
error: error while loading GCC, class file 'jacop/target/classes/org/jacop/constraints/GCC.class' is broken
(class java.lang.NullPointerException/null)
src/main/scala/org/jacop/scala/package.scala:83: error: org.jacop.constraints.GCC does not have a constructor
    val c = new GCC( x.asInstanceOf[Array[org.jacop.core.IntVar]], y.asInstanceOf[Array[org.jacop.core.IntVar]] )
            ^
warning: Class org.jacop.constraints.Diff.Pair not found - continuing with a stub.
error: error while loading Diff, class file 'target/classes/org/jacop/constraints/Diff.class' is broken
(class java.lang.NullPointerException/null)
two warnings found
three errors found
```

The Scala compile error is caused by scalac not finding the inner classes `GCC.Component` and `Diff.Pair` when referred to in type signatures.

Here is an example of an incorrect type signature for a field in the bytecode for `GCC`:

```
  java.util.ArrayDeque<org.jacop.constraints.GCC.Component> S2;
    flags:
    Signature: #28                          // Ljava/util/ArrayDeque<Lorg/jacop/constraints/GCC.Component;>;
```

## Comments

### Jesper Öqvist - 2018-01-12

Fix error in class type signatures

The type signature of a nested class should refer to the class by its bytecode
name.

fixes #294 (bitbucket)


→ <<cset bbb70a746bce>>
