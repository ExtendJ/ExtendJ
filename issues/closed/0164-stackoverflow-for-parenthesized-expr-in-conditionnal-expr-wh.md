# Stackoverflow for parenthesized expr in conditionnal expr while searching assignment context

**Status:** resolved

The following piece of code produces a stack overflow when trying to compile it :
```
#!java
package p;

class C {
    C(String str){}

    String getText(){return "";}

    void init(Object o){
    	new C(o == null ? (getText()) : "");
    }
}
```

## Comments

### Jesper Öqvist - 2016-03-17

This error seems to be caused by a circular definition of the `isPolyExpression` and `assignmentContext` attributes. If this circularity can be removed that would be the preferred solution, but if the Java specification requires this to be a fixed-point computation then the attribute specifications need to be updated and declare the appropriate attributes as circular. This may require a new version of JastAdd to allow caching of non-circular attributes in a circular evaluation.

### Jesper Öqvist - 2016-03-17

After a quick look at the Java 8 specification, Chapter 5. Conversions and Contexts (https://docs.oracle.com/javase/specs/jls/se8/html/jls-5.html), I could not find a clear definition of what an assignment context is. It may be that the rules for assignment context in ExtendJ are too general.

### Jesper Öqvist - 2016-03-17

This test case implies that conditional expression operands have the same assignment context as the conditional expression itself:

```java
// Test type inference for lambda operands of conditional expression.
// .result=COMPILE_PASS
public class Test {
  public interface TestInterface<T> {
    public T functMethod(Integer a);
  }

  public interface SubTestInterface<T> extends TestInterface<T> {
  }

  public static void main(String[] args) {
    TestInterface<Integer> t = args.length == 3
        ? (SubTestInterface<Integer>) a -> a
        : b -> b.intValue() + 4;
  }
}
```

Updating the `assignmentContext` attribute so that conditional expression operands look further up in the tree for an assignment context removes the circularity and seems to fix this problem (and I don't see any regressions).

### Jesper Öqvist - 2016-03-17

Remove circularity in assignmentContext attribute

Fixed circular definitions of assignmentContext, invocationContext, and
isPolyExpression, by letting ConditionalExpr operands receive the
assignementContext/invocationContext of the ConditionalExpr itself.

fixes #164 (bitbucket)


→ <<cset 42843acc9ed1>>

### Loïc Girault - 2016-03-17

You scared me when you mention the necessity of a new version of JastAdd ! Happy to see it so quickly fixed !
