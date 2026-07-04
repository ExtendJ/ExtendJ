# No error if constant pool overflows

**Status:** resolved

JastAddJ does not report an error if the constant pool overflows, for example if there are too many unique string literals in a class file.

## Comments

### Jesper Öqvist - 2015-02-05

Throw error if constant pool overflows

fixes #85 (bitbucket)


→ <<cset be43f01933bc>>

### Jesper Öqvist - 2015-02-05

Throw error if constant pool overflows

fixes #85 (bitbucket)


→ <<cset be43f01933bc>>
