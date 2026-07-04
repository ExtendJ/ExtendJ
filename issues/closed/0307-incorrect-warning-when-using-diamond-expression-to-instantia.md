# Incorrect warning when using diamond expression to instantiate EnumMap

**Status:** resolved

*ExtendJ 8.1.2-12-g7f1a553d Java SE 8*

ExtendJ fails to infer the right type for `EnumMap` when omitting the type arguments:

```java
// Using parameterized class type to infer the diamond type.
// .result: COMPILE_PASS
import java.util.EnumMap;
public class Test {
  enum Beer {
    OMNIPOLLO_NOA,
    WISBY_LAGER,
  }
  EnumMap<Beer, String> beerKind = new EnumMap<>(Beer.class);
}
```

Expected result: should compile without warning

Actual result: gives the following warning (x2):

```
Test.java:9: warning: unchecked conversion from raw type java.util.EnumMap to generic type java.util.EnumMap<Test.Beer, java.lang.String>
```

## Comments

### Jesper Öqvist - 2019-03-19

Improve type inference for diamond expressions

Type variable substitution now substitutes inside type bounds for more accurate
diamond type inference.

fixes #307 (bitbucket)


→ <<cset d4d25af76352>>
