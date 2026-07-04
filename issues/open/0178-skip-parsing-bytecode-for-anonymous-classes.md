# Skip parsing bytecode for anonymous classes

Anonymous classes are not needed for doing static analysis during compilation so we could skip loading their bytecode to save time.

## Comments

### Jesper Öqvist - 2016-07-25

See also #175

### Jesper Öqvist - 2016-07-25

I did a quick check with Javac to confirm that it skips reading bytecode for anonymous classes.
