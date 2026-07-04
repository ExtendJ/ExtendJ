# Multiple local classes can share the same name

**Status:** resolved

JastAddJ accepts multiple local classes with the same name. This should not be allowed.

Example:

    :::java
    void m() {
        class A {};
        class A {};
    }

## Comments

### Jesper Öqvist - 2013-08-30

Added name checking for local class decls

fixes issue #25 (bitbucket)


→ <<cset ab5921bdaf5b>>
