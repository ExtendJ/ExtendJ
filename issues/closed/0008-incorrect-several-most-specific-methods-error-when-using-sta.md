# Incorrect several most specific methods error when using static imports

**Status:** resolved

JastAddJ `7.1.1-29-g550f24d Java SE 7` gives an incorrect error when building junit-4.11:

````
tmp/src/junit-r4.11/src/test/java/org/junit/tests/experimental/rules/TestWatcherTest.java:72:
  Semantic Error: several most specific methods for fail()
    fail() in org.junit.Assert
    fail() in junit.framework.Assert
````

In the mentioned source file there are static imports of two different methods from `junit.framework.Assert` and `org.junit.Assert`:

````
import static junit.framework.Assert.fail;
...
import static org.junit.Assert.assertThat;
````

So the `fail()` method should refer to the static method in `junit.framework.Assert`.

## Comments

### Jesper Öqvist - 2013-05-31

Fixed single static import error

fixes #8


→ <<cset aa4bf860a696>>
