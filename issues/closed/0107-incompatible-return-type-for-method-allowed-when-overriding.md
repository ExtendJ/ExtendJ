# Incompatible return type for method allowed when overriding method in interface of abstract superclass

**Status:** resolved

**JastAddJ 7.1.1-305-gb3a081f Java SE 7**

When overriding an interface method it is possible to use an incompatible return type for the overriding method if a method with the same name is present in an abstract superclass. The test case below illustrates the problem:

```
// Overriding method from interface of abstract superclass with incompatible return type
// .result=COMPILE_FAIL
interface I {
    int m();
}

abstract class S implements I {
    public int m() {
        return 0;
    }
}

public class Test extends S {
    public byte m() {
        return 0;
    }
}
```

## Comments

### Jesper Öqvist - 2015-04-14

The part of JastAddJ that should check for incompatible return types in overriding methods uses the `interfacesMethodsIterator` attribute to find interface methods to check, however it does not work if the interface method is "shadowed" by a method in an abstract superclass. We might be able to fix the problem by altering the behaviour of `interfacesMethodsIterator`, but this may also cause other parts of the analysis to behave incorrectly.

### Jesper Öqvist - 2015-04-15

It seems like the only thing that needs to be changed to fix this issue is the `mayOverride` attribute. In the Java 5 module the attribute looked like this:

```
refine TypeHierarchyCheck eq MethodDecl.mayOverride(MethodDecl m) {
  return type().subtype(m.type());
}
```

This does not work as it should when both types are primitive, because it allows widening/narrowing primitive conversion when primitive return types should really be identical for overriding.

### Jesper Öqvist - 2015-04-15

Fixed error in mayOverride attribute

mayOverride now requires identical types when both types are primitive

fixes #107 (bitbucket)


→ <<cset 0c5d2a375f53>>
