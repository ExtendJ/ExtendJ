# Static field update for switch over enums

**Status:** resolved

_ExtendJ 8.1.2-85-gcff2884 Java 9_

When adding support for Java 9 and bytecode version 53 invalid bytecode for switch statements over enums is created.  The error “Update to static final field” appears for the regression tests enum/switch\_01p, enum/switch\_02p and the following shortened example:

‌

```
public class Test {
  public static void main(String[] args) {
    Egg egg = Egg.TURKEY;
    System.out.format("%s egg weighs %dg", egg, weight(egg));
  }
  static int weight(Egg egg) {
    switch (egg) {
      case TURKEY:
        return 1000;
    }
    return -1;
  }
}
enum Egg {
  TURKEY,
}
```

Expected **result:** “TURKEY egg weighs 1000g”

Actual **result:**

```
Exception in thread "main" java.lang.IllegalAccessError: Update to static final field Test.$SwitchMap$Egg attempted from a different method ($SwitchMap$Egg) than the initializer method <clinit>
at Test.$SwitchMap$Egg(Test.java)
at Test.weight(Test.java:29)
at Test.test(Test.java:13)
at Test.main(Test.java:7)
```

This appears to be because of Java 9 being stricter about updates to final fields and javac creates an anonumous class to contain the SwitchMap which extendJ doesn’t do.

‌

## Comments

### Jesper Öqvist - 2023-02-09

It looks like javac generates switch maps in their own anonymous classes in Java 9\+, that solution should work backwards for older Java versions as well.

### Jesper Öqvist - 2023-04-02

Generate enum switch maps in anonymous classes

An implicit anonymous class is created for each enum switch map, this
fixes incompatible code generating when generating bytecode for Java 9
or later versions (bytecode version 53.0+).

Changed the attribute TypeDecl.isAnonymous() from inherited to
synthesized.

Added new subclass SwitchMapDecl of AnonymousDecl for enum switch map
classes.

Fixes #318


→ <<cset dbd6240ff95c>>
