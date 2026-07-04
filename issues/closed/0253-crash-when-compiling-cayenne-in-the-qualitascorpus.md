# Crash when compiling Cayenne in the QualitasCorpus

**Status:** resolved

ExtendJ 8.0.1-220-gfec29ac Java SE 8

When compiling the Cayenne project from the QualitasCorpus ExtendJ crashes with a StackOverflowError. Increasing the stacksize does not seem to resolve the problem. In the test below I ran it with 1gb och stack space. Looking at the shape of the stacktrace there seems to be an infinite recursive loop in the compiler. The stacktrace is attached in the issue.

I compiled using: java -Xss1024m -Xms4096m -jar extendj.jar @systems_10\cayenne-3.0.1

## Attachments

- [crash.txt](../attachments/253/crash.txt) (uploaded by Sebastian Hjelm)

## Comments

### Jesper Öqvist - 2017-12-29

Here is a smaller test case that seems to trigger the same stack overflow:

```java
import java.util.*;

public class Test {
  public static void main(String[] args) {
    setParams(Collections.singletonMap("a", null));
  }

  static void setParams(Map<String, ?>... params) {
  }
}
```

### Jesper Öqvist - 2017-12-29

This bug only affected the Java 8 version. The Java 7 version of ExtendJ did not have this issue.

### Jesper Öqvist - 2017-12-29

Fix stack overflow in method applicability testing

Stack overflow was caused by circularity in method applicability testing.  This
has been solved in the same way as previously, by using a temporary bound
access to lock a method access during applicability testing.

fixes #253 (bitbucket)


→ <<cset 88bcade0a7ec>>
