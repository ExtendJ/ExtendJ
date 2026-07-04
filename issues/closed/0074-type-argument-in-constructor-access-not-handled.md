# Type argument in constructor access not handled

**Status:** resolved

The following test case generates no error in JastAddJ:

```
#!java

public class Test {
        class B {
                public <T> B(T t) {

                }
        }

        public void testMethod() {
                /* Should generate error, there exist no valid
                   generic constructor that can have an interger
                   as type argument but still accept a string as
                   parameter */
                B b1 = new <Integer> B("hej");
        }
}
```

And the following test case do generate an error:

```
#!java
public class Test {
        public class B {
                public B(int i) { }
        }

        public <T extends Integer> void testMethod(T t) {
                /* javac does not generate an error here, neither
                   version 7 nor 8. JastAddJ do on the other hand.
                   If the "int" in B:s constructor is changed to Integer
                   there is no error though. */
                B b = new B(t);
        }
}
```

## Comments

### Jesper Öqvist - 2015-04-02

I added the second test case to the regression test suite and it seems to work with the latest version of JastAddJ.

JastAddJ 7.1.1-302-g75236ca Java SE {5, 6, 7, 8} all produce the following error:

```
Errors:
tests/generics/constructor_03f/Test.java:15:
  Semantic Error: can not instantiate Test.B no matching constructor found in Test.B
```

### Jesper Öqvist - 2015-04-02

Oops, I just realized that I misread the comment in the second test case. Seems like method invocation conversion is not respected by JastAddJ.

### Jesper Öqvist - 2015-04-02

The reason method invocation conversion checks fail is because the type of the argument is a type variable which is not identified as an unboxable type. This is due to expressions like this in AutoBoxing.jrag:

```java
boolean canUnboxThis = !unboxed().isUnknown();
```

The unboxed attribute will return UnknownType for type variables. The unboxed attribute could be implemented for TypeVariable and  return the unboxed type of the first type bound (if one exists).

### Jesper Öqvist - 2015-04-02

Improved unboxed attribute

The unboxed attribute now computes unboxing for type variables.

fixes #74 (bitbucket)


→ <<cset 42f3ad6f8d5a>>
