# ExtendJ classes accessible by code being compiled

If you run extendj without `bootclasspath` or the system property `sun.boot.class.path`, then code being compiled can
reference the types inside ExtendJ.

For example, create `BadReference.java` and enter:

```
class BadReference {
  org.extendj.ast.Program p;
}
```

Compile with:

```
java -jar extendj.jar BadReference.java
```

The compilation should fail, but it passes.

## Comments

### Jesper Öqvist - 2022-08-25

This is difficult to fix. The standard library classes are accessed through `java.lang.ClassLoader.getSystemResource(String)` which will have access to ExtendJ because it is on the classpath at startup, at least when running via a Jar file. It should be possible to isolate ExtendJ classes using a custom ClassLoader, but that’s a lot of work to implement and get working correctly for a minimal improvement.
