# Error messages when using safevarargs annotation incorrectly

**Status:** resolved

*ExtendJ 8.0.1-196-gc01b244 JavaSE 8*

The error message when using safevarargs annotations incorrectly is not very informative and sometimes incorrect. The error message is "@SafeVarargs is only allowed for variable arity method and constructor declarations". This is incorrect if the method is a variable arity method that is not static or final.

## Comments

### Jesper Öqvist - 2017-11-21

More precise @SafeVarargs misuse errors

This adds more precise error messages when the @SafeVarargs annotation is used
incorrectly.

fixes #215


→ <<cset a9ab455c301d>>
