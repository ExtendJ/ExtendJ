# Nicer error message when constant pool overflows

**Status:** resolved

Generate a nicer error message for constant pool overflows. Currently a stack trace is printed when this occurs.

## Comments

### Jesper Öqvist - 2015-02-05

Erroneously closed by bad commit message.

### Jesper Öqvist - 2015-02-12

Added missing enclosing instance check

Extra check to report better error message for some cases where an enclosing
instance is missing, rather than throwing exception during code gen.

fixes #86 (bitbucket)


→ <<cset 701eb1351faf>>
