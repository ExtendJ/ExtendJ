# Conflicting single-static field imports

**Status:** resolved

**JastAddJ 7.1.1-316-g7209d06 Java SE 7**

Importing two fields with the same name using single-static import declarations should raise an error.

Excerpt from a simple test case:

```
// Multiple static imports of field with the same name
// .result=COMPILE_FAIL
import alfa.Alfa.gamma;
import beta.Beta.gamma;
public class Test {
        int i = gamma;
}
```

## Comments

### Jesper Öqvist - 2026-08-14

Revision 7209d06 which this issue was reported against already reported the ambiguity.
The current compiler diagnostic reported where the ambiguous import is used:

```
Test.java:9,11: error: several fields named gamma
    int gamma declared in alfa.Alfa
    int gamma declared in beta.Beta
```

Reporting the error at the use site rather than at the import matches javac,
and keeps unused conflicting imports legal (see issue 227).

Issue closed and regression test added: pkg/static_import_12f
