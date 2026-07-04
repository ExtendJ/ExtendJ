# Multiple matching incompatible method inheritance

**Status:** resolved

JastAddJ allows some inheritance of incompatible matching method declarations. Example:

    abstract class A {
            abstract void m(int i);
    }
    interface I {
            int m(int i);
    }
    abstract class Test extends A implements I {
    }

The above example should give a compile error because the types `A` and `I` are incompatible, but JastAddJ ignores this error.

## Comments

### Jesper Öqvist - 2014-03-03

Improved abstract method declaration checking

fixes issue #55 (bitbucket)


→ <<cset 02cc74fa0cbc>>
