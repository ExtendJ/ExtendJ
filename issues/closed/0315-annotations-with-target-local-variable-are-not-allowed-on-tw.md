# Annotations with Target=LOCAL_VARIABLE are not allowed on TWR resource declarations

**Status:** resolved

_ExtendJ version 8.1.2-68-g825a39d0_

A try-with-resources resource declaration should be able to be annotated with an annotation type that has `LOCAL_VARIABLE` target.

‌

Expected result:
This should compile without error:


```java
// Test that annotation with Target=LOCAL_VARIABLE can be used on a resource
// declaration.
// .result=COMPILE_PASS
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Target;

public class Test {
  interface Resource extends AutoCloseable { }
  interface ResourceBuilder {
    Resource build();
  }
  void test(ResourceBuilder builder) {
    try (@Annot Resource r1 = builder.build()) { } catch (Exception e) {}
  }
}

@Target(LOCAL_VARIABLE)
@interface Annot {
}
```


Actual result: Compile fails with the error:


```
Test.java:13: error: annotation type Annot is not applicable to this kind of declaration
```

‌

## Comments

### Jesper Öqvist - 2022-06-28

Allow annotations with Target=LOCAL_VARIABLE on ResourceDeclaration

fixes #315 (bitbucket)


→ <<cset 27eb8c0a98ed>>
