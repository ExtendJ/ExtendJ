# Bound check fails (extends)

\*ExtendJ 8.1.2-49-g2b9c6bd Java SE 8\*

The bound check fails when the WildcardsExtends has a parametric class declaration as extendsType\(\). ExtendJ reports a compile time error, while nothing should be reported.

```java
class A<Q> {}

public class B<R extends A<R>>  {
    public static void main(String[] args) {
        B<? extends A<?>> c;
    }
}
```

‌

Expected result: \* NOTHING \*

Actual result: `error: type argument 1 is of type ? extends A which is not within the bounds of type parameter R (R extends A<R>)`

‌
