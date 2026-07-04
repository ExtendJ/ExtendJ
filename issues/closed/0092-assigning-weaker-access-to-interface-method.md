# Assigning weaker access to interface method

**Status:** resolved

**JastAddJ 7.1.1-268-ge381205 Java SE 7**

It is possible to implement an interface method with weaker access privileges. To do this, extend a class not implementing the interface with an equivalent declaration to the one in the implementing class.

```
// Test assigning weaker access to implemented interface method
// .result=COMPILE_FAIL
interface I {
        void m();
}
class A {
        void m() {
        }
}
class Test extends A implements I {
        void m() {// error: weaker access privileges
        }
}
```

## Comments

### Jesper Öqvist - 2015-02-10

This also works if the declarations in `A` and `Test` are preceded by `protected`.

### Jesper Öqvist - 2015-02-11

This bug is caused by the `ancestorMethods` attribute not including interface methods for the class it is evaluated for if an ancestor method is found in its direct superclass. This is an error because it leads to the circumvention of the access modifier overriding checks. Simple fix for this is to always add interface methods in the `ancestorMethods` set. In LookupMethod.jrag at line 511:

```diff
           set = set.add(m);
       }
     }
-    if (set.size() != 1 || ((MethodDecl)set.iterator().next()).isAbstract()) {
       for (Iterator iter = interfacesMethodsSignature(signature).iterator(); iter.hasNext(); ) {
         MethodDecl m = (MethodDecl)iter.next();
         set = set.add(m);
       }
-    }
     if (!hasSuperclass()) return set;
     if (set.size() == 1) {
       MethodDecl m = (MethodDecl)set.iterator().next();
```

### Jesper Öqvist - 2015-02-11

Check overriding of interface methods fix

fixes #92 (bitbucket)


→ <<cset 45f31b2def9a>>
