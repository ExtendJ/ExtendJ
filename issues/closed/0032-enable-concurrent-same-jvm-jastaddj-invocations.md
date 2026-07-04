# Enable concurrent same-JVM JastAddJ invocations

**Status:** resolved

JastAddJ has some static fields which inhibit concurrently executing JastAddJ instances in the same JVM. The static global mutable fields should be removed and we need to support concurrent execution.

## Comments

### Jesper Öqvist - 2016-02-24

This has been working for some time now, and is even used to run the regression tests faster.
