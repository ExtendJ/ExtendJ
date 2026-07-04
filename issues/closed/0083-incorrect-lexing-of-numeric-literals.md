# Incorrect lexing of numeric literals

**Status:** resolved

**JastAddJ 7.1.1-252-gbd031b3**

The following correct expression gives a parsing error due to too greedy lexing:

```
int x = 0;
int y = 0xE-x;
```

Also, the following incorrect numeric literals are accepted by JastAddJ:

```
double _ = .0E; // missing exponent
double _ = .0eD; // missing exponent
```

Also, incorrect errors about rounding to zero are generated:

```
Semantic Error: It is an error for nonzero floating-point 0x0P-010 to round to zero
Semantic Error: It is an error for nonzero floating-point 0x0.p0f to round to zero
```

## Comments

### Jesper Öqvist - 2015-01-29

Improved numeric literal parsing

* Improved numeric literal parsing
* Improved floating point literal error messages

fixes #83 (bitbucket)


→ <<cset fb243f8a28ad>>
