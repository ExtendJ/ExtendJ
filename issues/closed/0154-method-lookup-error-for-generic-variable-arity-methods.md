# Method lookup error for generic variable arity methods

**Status:** resolved

**8.0.1-90-g85f20a1**

There is a method lookup error that incorrectly causes multiple methods to be most specific for this test case:

```java
// .result=COMPILE_PASS
public class Test {
  @SafeVarargs
  public static <T> void perform(Subject<? extends T>... subjects) {
    Helper.perform(subjects); // Calls the first helper method.
  }
}

class Helper {
  // First helper method:
  @SafeVarargs
  public static <T> void perform(Subject<? extends T>... subjects) {
  }

  // Second helper method:
  @SafeVarargs
  public static <T> void perform(T... subjects) {
  }
}

class Subject<T> {
}
```

ExtendJ gives the following error for this test case:

```
    [junit] tests/varargs/method_01p/Test.java:5: error: several most specific methods for perform(subjects)
    [junit]     perform(Subject[]) in Helper
    [junit]     perform(Subject[]) in Helper
```

Javac does not complain about several most specific methods in this case.

## Comments

### Jesper Öqvist - 2016-03-10

ExtendJ does not complain about the following test case:

```
// .result=COMPILE_PASS
public class Test {
  public static <T> void perform(Subject<? extends T> subject) {
    Helper.perform(subject); // Calls the first helper method.
  }
}

class Helper {
  // First helper method:
  public static <T> void perform(Subject<? extends T> subject) {
  }

  // Second helper method:
  public static <T> void perform(T subject) {
  }
}

class Subject<T> {
}
```

This shows that the issue is specifically for variable arity calls.

### Jesper Öqvist - 2016-03-10

Improve bounds checking for overload resolution

Improved method overload resolution for methods with generic argument types.

fixes #154 (bitbucket)


→ <<cset fde66578035a>>
