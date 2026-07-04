# Enum constructors allowed to be declared public or protected

**Status:** resolved

**JastAddJ 7.1.1-273-g6875d14 Java SE 7**

JastAddJ allows enum constructors to be declared public or protected, which should not be allowed.

Test case for public:

```
// Enum constructors can not be public
// .result=COMPILE_FAIL
enum E {
        ;
        int i;
        public E() {
                i = 3;
        }
}
```

## Comments

### Jesper Öqvist - 2015-02-12

Don't transform enum constructor modifiers

Check for incorrect access modifiers on enum constructors rather
than transforming the modifiers.

fixes #97 (bitbucket)


→ <<cset fb550a2777e6>>
