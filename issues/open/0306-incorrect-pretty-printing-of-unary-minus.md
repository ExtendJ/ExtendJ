# Incorrect pretty-printing of unary minus

Unary minus expressions are printed without a space between the minus and the negated expression. This can cause confusion with prefix decrement and leads to the pretty-printed program having the wrong meaning, for example:

```java
b = - - a;
```

is printed as

```java
a = --b;
```
