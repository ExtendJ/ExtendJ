# Integer literal lexing error

**Status:** resolved

The integer literal `0xCE-256` is not correctly tokenized by ExtendJ 8.0.1-151-g5196a27 Java SE 8:

```
tests/lex/num_16p/Test.java:0,0: error: in hexadecimal literal "0xCE-256": unexpected character '-'; not a valid digit
```

## Comments

### Jesper Öqvist - 2017-04-04

Fix error in numeric literal lexing

This fixes a problem in lexing hexadecimal literals too aggressively,
causing for example the following subtraction to be tokenized as a
numeric literal: "0xE-1".

fixes #185 (bitbucket)


→ <<cset bd2ae9b74208>>
