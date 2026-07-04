# Variable lookup does not find previous resource declaration inside resource list of try-with-resources statement

**Status:** resolved

**ExtendJ 8.0.1-119-gfbb1555 Java SE 8**

Variable lookup does not find previous resource declaration inside resource list of try-with-resources statement. Test case:

```java
// Test that variable lookup can find a previous resource declaration inside
// the resource declaration list of try-with-resources statement.
// .result=COMPILE_PASS
public class Test {
  interface Resource extends AutoCloseable { }
  interface ResourceBuilder {
    Resource build();
    Resource build(Resource r);
  }
  void test(ResourceBuilder builder) {
    try (Resource r1 = builder.build(); Resource r2 = builder.build(r1)) {
    } catch (Exception e) {
    }
  }
}
```

ExtendJ output:

```
Test.java:11,69: error: no field named r1 is accessible
```

## Comments

### Jesper Öqvist - 2016-04-04

Fix variable lookup error in TWR resource list

fixes #170 (bitbucket)


→ <<cset 05749c53076c>>
