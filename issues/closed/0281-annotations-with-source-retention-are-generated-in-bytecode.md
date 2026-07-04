# Annotations with source retention are generated in bytecode

**Status:** resolved

*ExtendJ 8.1.0-27-gcd7effa Java SE 5*

The `Trait` annotation in the following example should not be generated in the bytecode output for the class `An6`:

```java
import java.lang.annotation.*;

@Trait("magical") public class An6 {
  public static void main(String[] args) {
  }
}

@Retention(RetentionPolicy.SOURCE)
@interface Trait {
  String value() default "";
}
```

Expected result: no annotations for class `An6` in bytecode.

Actual result: the bytecode contains the `Trait` annotation:

```
javap -v tmp/An6.class
Classfile tmp/An6.class
  Last modified Jan 8, 2018; size 405 bytes
  MD5 checksum 9592f4fd0641b17a193026c2b216286c
  Compiled from "An6.java"
public class An6
  SourceFile: "An6.java"
  RuntimeInvisibleAnnotations:
    0: #21(#22=s#23)
```

## Comments

### Jesper Öqvist - 2018-01-08

This regression was caused by commit 3b7bc066bcd57039abeb3e5e81ab799a03ad444e. I need to add a regression test for this and revert the relevant part of the commit.

### Jesper Öqvist - 2018-01-08

Revert "Code deduplication"

This reverts commit 3b7bc066bcd57039abeb3e5e81ab799a03ad444e.

This revert is needed because Annotation.isRuntimeVisible() is not the inverse
of Annotation.isRuntimeInvisible() (the CLASS retention policy was not handled
correctly).

fixes #281 (bitbucket)

Conflicts:
	java5/backend/AnnotationsCodegen.jrag


→ <<cset df37aa8c47b7>>
