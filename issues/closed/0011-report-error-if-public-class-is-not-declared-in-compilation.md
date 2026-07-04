# Report error if public class is not declared in compilation unit with same name

**Status:** resolved

Javac reports an error if a public class is not declared in a compilation unit with a matching name. See for example this output from javac 1.7.0_21:

````
T1.java:1: error: class T2 is public, should be declared in a file named T2.java
public class T2 {
       ^
````

## Comments

### Jesper Öqvist - 2016-02-24

Issue #42 was marked as a duplicate of this issue.

### Jesper Öqvist - 2022-06-30

Add error for type name not matching source file name

fixes #11 (bitbucket)


→ <<cset 46e0c72a2a1f>>
