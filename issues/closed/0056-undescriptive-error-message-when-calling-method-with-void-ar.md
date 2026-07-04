# Undescriptive error message when calling method with void argument

**Status:** resolved

Test case:

    void f(int i) {
    }
    void m() {
      f(f(3));
    }

JastAddJ gives the following error message:

    Test.java:5:
      Semantic Error: no method named f(void) in Test matches. However, there is a method f(int)

It would be better if JastAddJ simply said that the argument `f(3)` is void and calling a method with a void argument is not allowed.

## Comments

### Jesper Öqvist - 2014-02-26

Improved method access type errors

fixes issue #56 (bitbucket)


→ <<cset ab0539ed0284>>
