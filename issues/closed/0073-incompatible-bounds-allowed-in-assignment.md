# Incompatible bounds allowed in assignment

**Status:** resolved

The following test case generates no error in JastAddJ:

```
#!java

public class Test {
        public <T extends Number> T method() {
                return null;
        }

        public void testMethod() {
                /* Should generate error, the bound on T
                   makes string an invalid type to assign to */
                String s = method();
        }
}
```

## Comments

### Jesper Öqvist - 2015-04-14

This issue might have been fixed. With **JastAddJ 7.1.1-305-gb3a081f Java SE 7** I get the following error:

```
Errors:
test/Test.java:9:
  Semantic Error: no method named method() in Test matches. However, there is a method method()
```

Changing the declared return type to String removes the error message.

I'll add a test for this in the regression test suite.

### Jesper Öqvist - 2015-04-14

Test added as `tests/generics/cast_02f`. The issue is very similar to the one tested by `tests/generics/cast_01f`, and I think it was fixed at the same time. See issue #105

### Jesper Öqvist - 2015-04-15

The above test was not fixed by the same commit that fixed issue #105, it was fixed by the commit 75236ca203dbb34f4e2ac31383c33e6800da2d13

It seems like the issue was fixed due to method type inference algorithm not finding a matching type for T and produces UnknownType as the type argument (though it should be resulting in Number?). UnknownType is then bounds-checked against the generic method type parameter and it is not within bounds so no matching method is found. Seems like something that needs to be fixed in the generic method type inference algorithm, but that is a separate issue.
