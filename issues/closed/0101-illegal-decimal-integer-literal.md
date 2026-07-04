# Illegal decimal integer literal

**Status:** resolved

**JastAddJ 7.1.1-282-g352bc41 Java SE 7**

The integer literal 2147483648 does not fit in an `int` and should not be allowed.

## Comments

### Jesper Öqvist - 2015-02-14

Added illegal integer literal error message

fixes #101 (bitbucket)


→ <<cset d654f469e8b8>>
