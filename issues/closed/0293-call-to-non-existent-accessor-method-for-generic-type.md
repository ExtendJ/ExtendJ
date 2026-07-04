# Call to non-existent accessor method for generic type

**Status:** resolved

*ExtendJ 8.1.0-54-g027b9c7 Java SE 8*

The following test causes broken generated code:

```java
@SuppressWarnings("unchecked")
public class Test<E> {
  private Object value = "hi mom";

  public static void main(String[] args) {
    System.out.println(toString(new Test()));
  }

  static String toString(Test an) {
    return an.value.toString();
  }
}
```

Expected result: should print "hi mom"

Actual result: not runnable:

```
Exception in thread "main" java.lang.NoSuchMethodError: Test.get$value$access$1(LTest;)Ljava/lang/Object;
     at Test.toString(Test.java:10)
     at Test.main(Test.java:6)
```

## Comments

### Jesper Öqvist - 2018-01-11

Fix non existent accessor use for generic type

fixes #293 (bitbucket)


→ <<cset dd7f702253a7>>
