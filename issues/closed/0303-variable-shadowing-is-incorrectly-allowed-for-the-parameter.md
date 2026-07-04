# Variable shadowing is incorrectly allowed for the parameter of an enhanced-for loop

**Status:** resolved

*ExtendJ 8.1.1-26-gba7e55f Java SE 8*

This program compiles when it should fail to compile:

```java
public class Test {
  public static void main(String[] args) {
    String p;
    for (String p : args)
      System.out.println(p);
  }
}
```

For reference, this is the error reported by javac:

```
Test.java:4: error: variable p is already defined in method main(String[])
    for (String p : args)
                ^
1 error
```

## Comments

### Jesper Öqvist - 2018-03-21

Fix incorrect name lookup for for-each parameter

The lookupVariable() attribute now finds preceding declarations for an
enhanced-for parameter.

fixes #303 (bitbucket)


→ <<cset e98a98041cd5>>
