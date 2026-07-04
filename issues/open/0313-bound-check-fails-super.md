# Bound check fails (super)

\*ExtendJ 8.1.2-49-g2b9c6bd Java SE 8\*

The bound check fails when the WildcardsSuper has a parametric class declaration as superType\(\). ExtendJ does not report any compile time error while a bound check failure should be reported.

```java
class A<Q> {}

public class B<R extends A<R>>  {
    public static void main(String[] args) {
        B<? super A<?>> c;
    }
}
```

‌

Expected result:  \(error reported by javac\):

```
error: type argument ? super A<?> is not within bounds of type-variable R
B<? super A<?>> c;
  ^
where R is a type-variable:
R extends A<R> declared in class B
```

Actual result: \* NOTHING \*

‌

‌
