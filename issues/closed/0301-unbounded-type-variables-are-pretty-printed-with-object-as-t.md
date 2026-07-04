# Unbounded type variables are pretty-printed with Object as type bound

**Status:** resolved

*ExtendJ 8.1.1*

The following input does not pretty-print correctly:

```java
class Test<T> {
}
```

Expected result: The pretty-printed output should be identical to the input.

Actual result: An explicit type bound is added to the type variable T:

```java
class Test<T extends java.lang.Object> {
}
```

## Comments

### Jesper Öqvist - 2018-03-06

This issue is caused by a rewrite that adds the `java.lang.Object` bound to any `TypeVariable` that does not have an explicit type bound.

From `java5/frontend/GenericTypeVariables.jrag`:

```java
  // Set java.lang.Object as the type bound if a type variable has no
  // explicit type bound.
  rewrite TypeVariable {
    when (getNumTypeBound() == 0)
    to TypeVariable {
      return new TypeVariable(
          new Modifiers(),
          getID(),
          new List<BodyDecl>(),
          new List<Access>(new TypeAccess("java.lang", "Object")));
    }
  }
```

### Jesper Öqvist - 2018-03-06

Remove TypeVariable rewrite

Removed a JastAdd rewrite that normalized the TypeBound list child of
TypeVariable nodes.

Renamed TypeVariable.TypeBound to TypeVariable.Bound.

Added attributes to provide the previous normalized view of the type bounds.

fixes #301 (bitbucket)


→ <<cset 08792210dd72>>
