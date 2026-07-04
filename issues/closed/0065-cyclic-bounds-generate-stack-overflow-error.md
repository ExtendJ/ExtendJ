# Cyclic bounds generate stack overflow error

**Status:** resolved

When a bound extends itself, a stack overflow error occurs instead of a normal error message. For example, the code below will generate a stack overflow error when compiled with JastAddJ:


```
#!java

interface Y { <S extends S> void execute(S s); }
```

## Comments

### Jesper Öqvist - 2022-08-24

Fix stack overflow for cyclic bounds erasure

This fixes a stack overflow in the erasure() attribute when there is a cyclic
bound in a type variable.

There needs to be an error check added for cyclic bounds now that the compiler
no longer crashes whenever there is a cyclic bound.

fixes #65 (bitbucket)


→ <<cset c635f52a0b20>>
