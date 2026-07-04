# Bug in MethodDecl.lessSpecificThan?

**Status:** resolved

Hi,

The attribute  MethodDecl.lessSpecificThan() as refined in /java5/frontend/MethodSignature.jrag:223 (reproduced below for convenience) will throw a NullPointerException if the called such that one of the methods has a non-zero number of arguments (say `k` arguments) and the other has zero arguments. In this case, `num` will be `k` and inside the loop, if `m` is the method with no arguments, a parameter will be accessed at the index `-1`, calling `.type()` on which will throw the exception. Does that make sense?

```
  refine MethodDecl eq MethodDecl.lessSpecificThan(MethodDecl m) {
    int numA = getNumParameter();
    int numB = m.getNumParameter();
    int num = Math.max(numA, numB);
    for (int i = 0; i < num; i++) {
      TypeDecl t1 = getParameter(Math.min(i, numA - 1)).type();
      TypeDecl t2 = m.getParameter(Math.min(i, numB - 1)).type();
      if (!t1.instanceOf(t2) && !t1.withinBounds(t2)) {
        return true;
      }
    }
    return false;
  }
```

Thanks!

## Comments

### Jesper Öqvist - 2017-11-07

That is interesting! The `lessSpecificThan` attribute is used after applicable methods have been found. In Java 4 all applicable methods had to have the same number of formal parameters, but in Java 5 one could be variable arity and the other fixed arity.

Are you aware of any test case that causes an exception in the lessSpecificThan attribute?

### Jesper Öqvist - 2017-11-07

I looked at the possible calling paths for `lessSpecificThan`. I am pretty sure that there is no error for regular method declarations, but in the case of diamond accesses, there might be a bug:

* `MethodDecl.lessSpecificThan(MethodDecl)` is only called by `MethodDecl.moreSpecificThan(MethodDecl)`
* `MethodDecl.moreSpecificThan(MethodDecl)` is called by `MethodAccess.moreSpecificThan(MethodDecl, MethodDecl)` and `DiamondAccess.mostSpecific(SimpleSet<MethodDecl>, MethodDecl)`
* `MethodAccess.moreSpecificThan(MethodDecl, MethodDecl)` is only called by `MethodAccess.mostSpecific(SimpleSet<MethodDecl>, MethodDecl)`.

In the method case, all methods are first tested for subtyping applicability, which always selects non-variable arity methods before variable arity methods. This means that a method with zero formal parameters will not be tested if it is less specific against a variable arity method, so we won't get that -1 case.

In the diamond access case, the procedure looks incorrect because it always compares most specific methods, instead of picking fixed arity methods first. Here is the context for the diamond case:

```java
  protected SimpleSet<MethodDecl> DiamondAccess.chooseConstructor() {
    ClassInstanceExpr instanceExpr = getClassInstanceExpr();
    TypeDecl type = getTypeAccess().type();

    assert instanceExpr != null;
    assert type instanceof ParClassDecl;

    GenericClassDecl genericType = (GenericClassDecl) ((ParClassDecl) type).genericDecl();

    List<StandInMethodDecl> placeholderMethods = genericType.getStandInMethodList();

    SimpleSet<MethodDecl> maxSpecific = emptySet();
    Collection<MethodDecl> potentiallyApplicable = potentiallyApplicable(placeholderMethods);
    for (MethodDecl candidate : potentiallyApplicable) {
      if (applicableBySubtyping(instanceExpr, candidate)
          || applicableByMethodInvocationConversion(instanceExpr, candidate)
          || applicableByVariableArity(instanceExpr, candidate)) {
        maxSpecific = mostSpecific(maxSpecific, candidate);
      }
    }
    return maxSpecific;
  }
```

### Jesper Öqvist - 2017-11-07

Another unrelated error in Diamond type inference prevented the problematic case from happening (see issue #207). Fixing issue #207 makes it possible to expose the bug and get a NullPointerException with the following test:

```java
public class Arity3 {
  public static void main(String[] args) {
    Foo<String> foo = new Foo<>();
  }

}

class Foo<T> {
  Foo(String... f) {
  }

  Foo() {
  }
}
```

### Jesper Öqvist - 2017-11-07

Fix Diamond type inference error

This fixes an error in Diamond type inference when used with a variable arity
constructor.

This also fixes an error causing Diamond type inference to use the wrong target
constructor. Diamond type inference should now use the same constructor
applicability rules as regular constructor accesses.

Also changed two attributes into inter-type methods because they where used
to construct candidate method NTAs (see "substituted" in Diamond.jrag).

fixes #207
fixes #206


→ <<cset 4b476a8a9784>>

### Pratik Fegade - 2017-11-07

Hi,

I am actually using the attribute in a static analysis that I have been building which is when I encountered the `NullPointerException`. Thanks for the swift resolution!

Pratik Fegade

### Jesper Öqvist - 2017-11-08

@pratikfegade OK, well I didn't really change anything to fix your issue then: you still have to make sure to not call it with a nullary and non-nullary method. I added a documentation comment on the attribute specifying that.

If you haven't already, I suggest looking at the attribute `MethodAccess.maxSpecific(Collection<MethodDecl>)` in `java5/frontend/MethodSignature.jrag`. That attribute implements the most specific method selection described in [JLS 6 §15.12.2](https://docs.oracle.com/javase/specs/jls/se6/html/expressions.html#15.12.2). First all potentially applicable methods are found, then those applicable by subtyping are chosen (phase 1), then if none were applicable by subtyping it continues to phase 2 and phase 3.

### Jesper Öqvist - 2017-11-08

@pratikfegade Actually, you will want to look in the file `java8/frontend/MethodSignature.jrag`, to see the Java 8 version of the method.
