# Conflicting single static imports

**Status:** resolved

**JastAddJ 7.1.1-315-g3a38bb0 Java SE 7**

If multiple types with the same simple name are imported using single static imports an error should be reported. JastAddJ does not currently report an error.

Part of test case illustrating the issue:

```
// Conflicting single-static imports
// .result=COMPILE_FAIL
import static alfa.Alfa.Gamma;
import static beta.Beta.Gamma;
public class Test {
}
```

Note that Alfa and Beta are top-level types, and Gamma is a nested in both Alfa and Beta.

## Comments

### Jesper Öqvist - 2015-04-28

Improved import checking

Check for duplicate single static imports.

fixes #113 (bitbucket)


→ <<cset 67db5c86415a>>
