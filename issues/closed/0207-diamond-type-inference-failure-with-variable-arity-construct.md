# Diamond type inference failure with variable arity constructors

**Status:** resolved

**ExtendJ 8.0.1-188-g8c4b435 Java SE 8**

Diamond type inference fails when a variable arity constructor is used.

Test case:

```java
public class Arity2 {
  void pass() {
    Foo<String> foo = new Foo<>("bar", "baz");
  }
}

class Foo<T> {
  Foo(String... f) {
  }

  Foo() {
  }
}
```

Expected output: no warnings

Actual output:

test/Arity2.java:3: warning: unchecked conversion from raw type Foo to generic type Foo<java.lang.String>

## Comments

### Jesper Öqvist - 2017-11-07

Fix Diamond type inference error

This fixes an error in Diamond type inference when used with a variable arity
constructor.

This also fixes an error causing Diamond type inference to use the wrong target
constructor. Diamond type inference should now use the same constructor
applicability rules as regular constructor accesses.

Also changed two attributes into inter-type methods because they where used
to construct candidate method NTAs (see "substituted" in Diamond.jrag).

fixes #207
fixes #206


→ <<cset 4b476a8a9784>>
