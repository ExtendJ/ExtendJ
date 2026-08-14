# Type variable substitution in method return type

**Status:** resolved

*ExtendJ v8.0.1-4-gcc1f6e3*

I tried to compile jEdit 5.2.0 using ExtendJ. While javac accept it, ExtendJ
reports an error in `org/gjt/sp/jedit/pluginmgr/Roster.java`.  Here is a minimal
example:

```java
class A { }

interface I0<X> {
  X get();
}

interface I {
  I0<? extends A> m();
}

class B {
  void mtest(I i) {
    I0<? extends A> i0 = i.m();
    A a = i0.get();
  }
}
```

Extendj complains that it cannot assign a value of type X to a variable of type A.
I fixed it by modifying `TypeVariable.substituteReturnType` in `java5/frontend/Generics.jrag`:

```java
public Access TypeVariable.substituteReturnType(Parameterization parTypeDecl) {
    if (parTypeDecl.isRawType()) {
      return erasure().createBoundAccess();
    }
    TypeDecl typeDecl = parTypeDecl.substitute(this);
    if (typeDecl instanceof WildcardType) {
      return createBoundAccess();
    } else if (typeDecl instanceof WildcardExtendsType) {

//       if (typeDecl.instanceOf(this)) {
        return ((WildcardExtendsType) typeDecl).extendsType().createBoundAccess();
//      } else {
//        return createBoundAccess();
//      }

    } else if (typeDecl instanceof WildcardSuperType) {

       return createBoundAccess();
    }
    return typeDecl.createBoundAccess();
  }
```

## Comments

### Jesper Öqvist - 2015-12-14

The minimal example seems to compile fine with `ExtendJ 8.0.1-16-g930e00d Java
SE 8`. I will try to compile jEdit 5.2.0 and see if I can reproduce the error
in that code base.

### Jesper Öqvist - 2026-08-14

The original compilation problem this issue described is no longer active.

I first verified the original issue description by compiling jEdit 5.2.0 with ExtendJ revision e0ff0a81.
The following diagnostics were reported:

```
org/gjt/sp/jedit/pluginmgr/Roster.java:315: can not assign variable entry of type java.util.zip.ZipEntry a value of type E
org/gjt/sp/jedit/PluginJAR.java:1416:       can not assign variable entry of type java.util.zip.ZipEntry a value of type E
org/gjt/sp/jedit/bsh/NameSource.java:63,5:  Syntactic Error: unexpected token "}"
org/gjt/sp/jedit/buffer/JEditBuffer.java:   Final field ... is not assigned before used   (×7)
org/jedit/io/Native2ASCIIEncoding.java:189: no constructor matches super(...)
```

The errors in `Roster.java` and `PluginJAR.java` match the original issue description and the minimal
example seems to accurately reflect the errors.  The relevant lines in `Roster.java` at line 311-315 are:

```java
  Enumeration<? extends ZipEntry> e = zipFile.entries();
  ZipEntry entry = e.nextElement();
```

These errors are no longer present in the latest ExtendJ version (e0ff0a81).
However, there are still 4 unrelated compilation errors preventing jEdit 5.2.0 from building cleanly
with ExtendJ.

I am closing this issue now since the original bug described by this issue is no longer active.

Regression test added in `rtest/tests/generics/wildcard_11p`.
