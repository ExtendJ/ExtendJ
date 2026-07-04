# Try-with-resources

**Status:** resolved

*ExtendJ 8.0.1-189-g6a0d36d JavaSE 8*

Resources in try-with-resources in java 7 must be assigned at declaration.

The following code will compile in ExtendJ but not in javac:

```
#!java
try (BufferedReader b) {
	 b = new BufferedReader(null);
}catch (IOException e) {
}
```

To compile with javac the assignment must be at the declaration. Like this:
```
#!java
try (BufferedReader b = new BufferedReader(null)) {
}catch (IOException e) {
}
```

## Comments

### Jesper Öqvist - 2017-11-08

Disallow resource declarations without initializer

This removes the parsing production for resource declarations without an
initializer expression (try-with-resources), because it is not part of the Java
specification.

fixes #209


→ <<cset 3bd32b6ab5f5>>
