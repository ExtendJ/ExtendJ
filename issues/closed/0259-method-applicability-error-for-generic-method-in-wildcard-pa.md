# Method applicability error for generic method in wildcard parameterized type

**Status:** resolved

*ExtendJ 8.1.0-4-ge296850 Java SE 7*

The following fails to compile:

```java
// .result: COMPILE_PASS
import java.util.*;
public class Test {
  void add(Container<? super X> xs) {
    xs.add(new Y());
  }

  static class X { }
  static class Y extends X { }
}

interface Container<T> {
  void add(T t);
}
```

Expected result: should compile.

Actual result:

```
tests/generics/wildcard_10p/Test.java:5: error: no method named add(Test.Y) in Container<? super X> matches. However, there is a method add(wildcards.? super X)
```

## Comments

### Jesper Öqvist - 2018-01-01

This issue is caused by incorrect subtyping rules for WildcardSuperType:

```diff
diff --git a/java5/frontend/GenericsSubtype.jrag b/java5/frontend/GenericsSubtype.jrag
index 051ff61..7e6ebe2 100644
--- a/java5/frontend/GenericsSubtype.jrag
+++ b/java5/frontend/GenericsSubtype.jrag
@@ -109,14 +109,14 @@ aspect GenericsSubtype {
   eq WildcardExtendsType.supertypeArrayDecl(ArrayDecl type) = false;
   eq WildcardExtendsType.supertypeNullType(NullType type) = true;

-  eq WildcardSuperType.supertypeClassDecl(ClassDecl type) = superType().subtype(type);
-  eq WildcardSuperType.supertypeInterfaceDecl(InterfaceDecl type) = superType().subtype(type);
-  eq WildcardSuperType.supertypeParClassDecl(ParClassDecl type) = superType().subtype(type);
-  eq WildcardSuperType.supertypeParInterfaceDecl(ParInterfaceDecl type) = superType().subtype(type);
-  eq WildcardSuperType.supertypeRawClassDecl(RawClassDecl type) = superType().subtype(type);
-  eq WildcardSuperType.supertypeRawInterfaceDecl(RawInterfaceDecl type) = superType().subtype(type);
-  eq WildcardSuperType.supertypeTypeVariable(TypeVariable type) = superType().subtype(type);
-  eq WildcardSuperType.supertypeArrayDecl(ArrayDecl type) = superType().subtype(type);
+  eq WildcardSuperType.supertypeClassDecl(ClassDecl type) = type.subtype(superType());
+  eq WildcardSuperType.supertypeInterfaceDecl(InterfaceDecl type) = type.subtype(superType());
+  eq WildcardSuperType.supertypeParClassDecl(ParClassDecl type) = type.subtype(superType());
+  eq WildcardSuperType.supertypeParInterfaceDecl(ParInterfaceDecl type) = type.subtype(superType());
+  eq WildcardSuperType.supertypeRawClassDecl(RawClassDecl type) = type.subtype(superType());
+  eq WildcardSuperType.supertypeRawInterfaceDecl(RawInterfaceDecl type) = type.subtype(superType());
+  eq WildcardSuperType.supertypeTypeVariable(TypeVariable type) = type.subtype(superType());
+  eq WildcardSuperType.supertypeArrayDecl(ArrayDecl type) = type.subtype(superType());

   /** @return {@code true} if this type is a wildcard type, possibly with type bounds. */
   syn boolean TypeDecl.isWildcard() = false;
```

### Jesper Öqvist - 2018-01-01

Fix error in WildcardSuperType subtyping equations

The equations for the supertype attributes for WildcardSuperType were
incorrect.

fixes #259 (bitbucket)


→ <<cset 5bb53b0caf1b>>
