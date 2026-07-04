# Type variable substitution in method return type

extendj v8.0.1-4-gcc1f6e3
I tried to compile jEdit5.2.0 using extendj. While javac accept it, extendj find an error in org/gjt/sp/jedit/pluginmgr/Roster.java.  Here is a minimal example :

```
#!java

class A{}

interface I0<X>{
    X get();
}

interface I {
    I0<? extends A> m();
}

class B{
    void mtest(I i){
        I0<? extends A> i0 = i.m();
        A a = i0.get();
    }
}
```
Extendj complains that it can not assign a value of type X to a variable of type A.
I fixed it by modifying TypeVariable.substituteReturnType in java5/frontend/Generics.jrag :

```
#!java

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

The minimal example seems to compile fine with `ExtendJ 8.0.1-16-g930e00d Java SE 8`. I will try to compile jEdit 5.2.0 and see if I can reproduce the error in that code base.
