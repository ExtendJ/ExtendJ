# Incorrect pretty-printing of unary minus

**Status:** resolved

Unary minus expressions are printed without a space between the minus and the negated expression. This can cause confusion with prefix decrement and leads to the pretty-printed program having the wrong meaning, for example:

```java
b = - - a;
```

is printed as

```java
a = --b;
```

## Resolution

The unary `-`, `+`, `--`, and `++` prefix operators are now printed with a
separating space if the operand starts with the same character.

See regression tests `pretty-print/unary_0{2,3}p`.
