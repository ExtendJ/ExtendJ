# Clean up Unknown in error messages

ExtendJ tends to report several unrelated errors that include references to the Unknown type whenever a name lookup fails. This is misleading and hides the root cause of the error.

For example, the test case:

```java
public class U1 {
  public static void main(String[] args) {
    int x = bar();
  }

  int bax() {
    return 0;
  }
}
```

Expected result:

```
test/U1.java:3: error: no method named bar() in U1 matches.
```

Actual result:

```
test/U1.java:3: error: can not assign variable x of type int a value of type Unknown
test/U1.java:3: error: no method named bar() in U1 matches.
```

## Comments

### Jesper Öqvist - 2017-12-29

The test case `generics/method_25f` generates an error referring to the Unknown type:

```
tests/generics/method_25f/Test.java:7: error: no method named assertEqulas(int, Unknown) in Test matches.
tests/generics/method_25f/Test.java:7: error: no method named ident(java.lang.String) in Test matches. However, there is a method ident(Test.ident(java.lang.Object)@U)
```

### Jesper Öqvist - 2018-01-30

Improve method lookup error message

This skips the undeclared method error if any argument to the method call has
Unknown type.

This change should remove redundant and misleading error messages referring to
the Unknown type when a method call argument type can't be decided.

see #249 (bitbucket)


→ <<cset 7bc61fc15c6b>>

### Jesper Öqvist - 2018-01-30

Skip error redundant assignment error message

It is not necessary to report an error message for assignments that have source
type Unknown, since the Unknown type is caused by some other error in the
source expression.

This removes a redundant and misleading error message referencing Unknown type.

see #249 (bitbucket)


→ <<cset 106d5098d7bf>>

### Jesper Öqvist - 2018-01-30

Skip redundant assignment error message

It is not necessary to report an error message for assignments that have source
type Unknown, since the Unknown type is caused by some other error in the
source expression.

This removes a redundant and misleading error message referencing Unknown type.

see #249 (bitbucket)


→ <<cset ff36116a0263>>

### Jesper Öqvist - 2018-03-08

Skip redundant return type error message

It is redundant to report error for returning an expression that has type
Unknown. The root cause of the error is in the returned expression, and will
lead to some other error message.

see #249 (bitbucket)


→ <<cset 5824637ea0a0>>

### Jesper Öqvist - 2018-03-11

Skip redundant import error message

Removed redundant import error messages for the Unknown type.

see #249 (bitbucket)


→ <<cset 2fdb66996911>>

### Jesper Öqvist - 2018-03-11

Skip redundant field initializing error message

Removed redundant error message mentioning the Unknown type.

see #249 (bitbucket)


→ <<cset 6549bb9e2161>>

### Jesper Öqvist - 2018-03-21

Unknown type is reported for the argument in an enhanced-for expression.
