# NullPointerException when running java 9

**Status:** resolved

*ExtendJ 8.0.1-196-gc01b244 JavaSE 9*

The compiler crashes with null pointer exception when running with java 9. The exception is thrown in org.extendj.ast.ClassPath line 95. This happens because the system property sun.boot.class.path is removed in java9 (see http://openjdk.java.net/jeps/261).

## Comments

### Jesper Öqvist - 2017-11-09

ExtendJ will still crash when running on Java 9 after fixing this because it can't find `java.lang.Object`.

### Jesper Öqvist - 2017-11-09

Fix NPE if classpath system properties are missing

This fixes NullPointerExceptions if either of the sun.boot.class.path or
java.ext.dirs system properties are unset.

fixes #211


→ <<cset bd08c516ac73>>
