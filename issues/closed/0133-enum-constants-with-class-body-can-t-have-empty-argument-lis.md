# Enum constants with class body can't have empty argument list

**Status:** resolved

**Extendj 8.0.1**

An error in the enum parser makes ExtendJ not allow the following enum declaration:

```java
// Enum constants can have a class body.
// .result=COMPILE_PASS
enum Test {
  MY_CONSTANT() {
    void m() {
    }
  }
}
```

The parser expects a non-empty argument list and gives a syntax error on the `RPAREN` (`)`) token.

## Comments

### Jesper Öqvist - 2016-01-29

Fix enum parsing error

Allow enum constant declarations with a class body to have an empty argument
list.

fixes #133 (bitbucket)


→ <<cset 152b130b1726>>
