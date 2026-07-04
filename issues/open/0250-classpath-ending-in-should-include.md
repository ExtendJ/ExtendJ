# Classpath ending in ':' should include '.'

ExtendJ 8.0.1-220-gfec29ac Java SE 8

In javac a classpath ending with ':' will also include the current directory.
Example:
```
#!bash
javac -cp libs/A.jar: B.java
```
is equivalent to
```
#!bash
javac -cp libs/A.jar:. B.java
```

This is different in ExtendJ.
