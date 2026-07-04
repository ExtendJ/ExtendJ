# Conflicting single-static field imports

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
