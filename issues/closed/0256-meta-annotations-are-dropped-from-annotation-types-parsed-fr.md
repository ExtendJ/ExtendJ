# Meta-annotations are dropped from annotation types parsed from bytecode

**Status:** resolved

*ExtendJ 8.0.1-260-g88bcade Java SE 8*

When an annotation type is loaded from bytecode, meta-annotations like `RuntimeRetention` are dropped. This results in incorrect runtime retention of any occurrences of the annotation.

This happens for example when using the `Parameters` annotation from JUnit4.11.

## Comments

### Jesper Öqvist - 2017-12-30

The test framework had to be updated to be able to write a regression test for this issue. The runtime classes are now compiled before running the tests, and must be linked explicitly via the classpath setting.

Added test `annotation/method_02p` for this issue:

```java
// Test that runtime retention is kept when loading an annotation from
// bytecode. The annotation is provided via the runtime library.
// https://bitbucket.org/extendj/extendj/issues/256/meta-annotations-are-dropped-from
// .classpath: @RUNTIME_CLASSES@
import java.lang.reflect.Method;

import runtime.Notes.Timeout;

public class Test {
  public static void main(String[] args) {
    Test test = new Test();
    Class klass = Test.class;
    for (Method method: klass.getDeclaredMethods()) {
      Timeout annotation = method.getAnnotation(Timeout.class);
      if (annotation != null) {
        long timeout = annotation.timeout();
        if (timeout != 100L) {
          throw new Error("unexpected timeout!");
        }
        return;
      }
    }
    throw new Error("could not find annotated method!");
  }

  @Timeout(timeout = 100) static public void m() { }

}
```

### Jesper Öqvist - 2017-12-31

The error is caused by modifiers (including annotations) being replaced for inner types when parsing inner class information.

The problematic line is at `java5/frontend/BytecodeAttributes.jrag:361`:

```java
            if (inner_class_info.name().equals(p.classInfo.name())) {
              if (AbstractClassfileParser.VERBOSE) {
                p.println("      Class " + inner_class_name + " is inner (" + inner_name + ")");
              }
              typeDecl.setID(inner_name);
/* ----> */   typeDecl.setModifiers(
                  AbstractClassfileParser.modifiers(inner_class_access_flags & 0x041f));
```

### Jesper Öqvist - 2017-12-31

Fix inner types annotation bytecode parsing issue

Annotations could be dropped when parsing bytecode for inner types.  This
occurred because the modifiers for the inner type were replaced by the
modifiers from the InnerClasses attribute.

This was an order-dependent issue: if the InnerClasses attribute was before the
RuntimeVisibleAnnotations attribute or RuntimeInvisibleAnnotations attribute,
then the annotations would be appended to the updated modifiers.

fixes #256 (bitbucket)


→ <<cset 1c6e3bd9aeda>>
