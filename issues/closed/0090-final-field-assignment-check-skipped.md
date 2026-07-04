# Final field assignment check skipped

**Status:** resolved

**JastAddJ 7.1.1-264-g6b559f3 Java SE 7**

If a class has multiple static final fields and one of them is uninitialized it seems like the unassigned final field check is skipped for that field. Test case:

```
// Test missing static final assignment error message.
// .result=COMPILE_FAIL
public class Test {
        static final int x = 0, y;
}
```

## Comments

### Jesper Öqvist - 2015-02-08

Fixed incorrect fallthrough equ for isDAbefore

fixes #90 (bitbucket)


→ <<cset 5962ff92995a>>
