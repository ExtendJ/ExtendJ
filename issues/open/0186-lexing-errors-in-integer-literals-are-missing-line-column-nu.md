# Lexing errors in integer literals are missing line/column numbers

*ExtendJ 8.0.1-151-g5196a27 Java SE 8*

Lexing errors like this one should have proper line and column numbers:

```
tests/lex/num_03f/Test.java:0,0: error: in decimal floating point literal ".0E": expected exponent after exponent specifier
```
