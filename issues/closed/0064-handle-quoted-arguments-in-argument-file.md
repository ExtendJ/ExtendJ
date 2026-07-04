# Handle quoted arguments in argument file

**Status:** resolved

JastAddJ can handle argument files, like javac does, however the support is only partial. JastAddJ for example does not handle quoted arguments such as:

    "s p a c e/Test.java"

JastAddJ 7.1.1-156-g6058a86 Java SE 7 output when using the above argument file:

    Error: neither a valid option nor a filename: "s
    Error: neither a valid option nor a filename: p
    Error: neither a valid option nor a filename: a
    Error: neither a valid option nor a filename: c
    Error: neither a valid option nor a filename: e/Test.java"

## Comments

### Jesper Öqvist - 2014-03-06

Improved argument list parsing

- Argument files are now expanded in-place
- Argument files may now contain comments and quoted strings
- A leading at-sign can be escaped by a double at-sign in the argument list

fixes issue #64 (bitbucket)


→ <<cset bc3a3b02e4fe>>
