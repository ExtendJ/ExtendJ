# Classfiles not generated for imported types

**Status:** resolved

JastAddJ does not generate classfiles for any imported types.

Example:

    :::java
    import p1.*;
    import p2.B;
    public class Test {
        public static void main(String[] args) {
            new A(); new B();
        }
    }

## Comments

### Jesper Öqvist - 2013-08-29

Classfiles need to be generated for from-source library compilation units.

### Jesper Öqvist - 2013-08-29

Generate code for imported from-source types

fixes issue #23 (bitbucket)
fixes issue #24 (bitbucket)


→ <<cset 65be7b95b7de>>
