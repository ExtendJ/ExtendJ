# Remove pretty-printing of implicit constructor invocation

**Status:** resolved

**JastAddJ 7.1.1-274-gba3100a Java SE 7**

If a constructor is declared without an explicit constructor invocation, then an implicit super constructor call is inserted. This means that the pretty-printing of such a constructor is not identical to the source constructor declaration.

## Comments

### Jesper Öqvist - 2015-02-12

Do not pretty-print implicit constructor calls

fixes #98 (bitbucket)


→ <<cset 61a89866a046>>
