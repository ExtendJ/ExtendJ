# Comparing to non-reifiable type in instanceof expression

**Status:** resolved

**JastAddJ 7.1.1-312-gb3bf40c Java SE 8**

JastAddJ allows the second part of an instanceof expression to reference a non-reifiable type. This goes against the JLS. See for example [JLS SE7 $15.20.2](https://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.20.2):

>It is a compile-time error if the ReferenceType mentioned after the instanceof operator does not denote a reference type that is reifiable (§4.7).

## Comments

### Jesper Öqvist - 2015-04-17

Check for non-reifiable type in instanceof rhs

fixes #109 (bitbucket)


→ <<cset bcfa5f1e4907>>
