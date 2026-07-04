# Exception checking error for anonymous class instance expressions

**Status:** resolved

When compiling Junit 4.11 with JastAddJ `7.1.1-44-g4420934 Java SE 7` I get the following incorrect error message:

````
junit-r4.11/src/main/java/org/junit/experimental/max/MaxCore.java:116:
  Semantic Error: the exception org.junit.runners.model.InitializationError is not thrown in the body of the try statement
````

Here is a small test that reproduces the same kind of error:

````java
import java.io.IOException;

public class Test {
        class Foo {
                Foo() throws IOException {
                        throw new IOException("oops");
                }
        }
        public void foo() {
                try {
                        new Foo() { };
                } catch (IOException e) {
                }
        }
}
````

## Comments

### Jesper Öqvist - 2013-06-02

Added test case.

### Jesper Öqvist - 2013-06-02

Add exceptions to anonymous class constructor

fixes #10


→ <<cset 0cd3fe5ca9e7>>
