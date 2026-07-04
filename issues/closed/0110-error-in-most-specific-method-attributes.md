# Error in most specific method attributes

**Status:** resolved

**JastAddJ 7.1.1-313-gbcfa5f1 Java SE 7**

The attributes in JastAddJ for finding the most specific method declaration for a method access seem to be incorrect.

The current implementation looks like this, in the Java 5 module:

```
  private static SimpleSet MethodAccess.mostSpecific(SimpleSet maxSpecific, MethodDecl decl) {
    if (maxSpecific.isEmpty()) {
      maxSpecific = maxSpecific.add(decl);
    } else {
      if (decl.moreSpecificThan((MethodDecl)maxSpecific.iterator().next())) {
        maxSpecific = decl;
      } else if (!((MethodDecl)maxSpecific.iterator().next()).moreSpecificThan(decl)) {
        maxSpecific = maxSpecific.add(decl);
      }
    }
    return maxSpecific;
  }
```

The attribute of `moreSpecificThan` lacks documentation, and the implementation contradicts what the attribute name implies that the attribute should compute. A better name for the attribute would have been `notLessSpecificThan`, because the attribute only returns false if the parameter list of the receiving method is empty or some parameter type in the argument method is a supertype of the corresponding argument type in the receiving method:

```
  refine MethodDecl eq MethodDecl.moreSpecificThan(MethodDecl m) {
    if (getNumParameter() == 0) {
      return false;
    }
    int numA = getNumParameter();
    int numB = m.getNumParameter();
    int num = Math.max(numA, numB);
    for (int i = 0; i < num; i++) {
      TypeDecl t1 = getParameter(Math.min(i, numA-1)).type();
      TypeDecl t2 = m.getParameter(Math.min(i, numB-1)).type();
      if (!t1.instanceOf(t2) && !t1.withinBounds(t2, Parameterization.RAW)) {
        return false;
      }
    }
    return true;
  }
```

The attribute `moreSpecificThan` should either be fixed to work as the name implies, or it should be renamed to `notLessSpecificThan`, which seems less useful.

A test case for the incorrect most specific method lookup:

```
// Test that ambiguous method access is reported.
// .result=COMPILE_FAIL
class ParType<T> {
    public void m(T x) { }
    public void m(Integer x) { }
}

public class Test {
    public void f(ParType<Integer> x) {
        x.m(123);// ambiguous method access!
    }
}
```

This test case should fail to compile, but with the current version of JastAddJ it passes.

## Comments

### Jesper Öqvist - 2015-04-24

Improved MethodDecl.moreSpecificThan attribute

The MethodDecl.moreSpecificThan attribute did not work as the name implied: it
could return true even when a method was not definitely more specific than
another method.

Added MethodDecl.lessSpecificThan helper attribute.

fixes #110 (bitbucket)


→ <<cset 2c013336b925>>
