# Method reference - Stack overflow error

**Status:** resolved

Java 8 backend at 0f06364

Hi, I have discovered a case that looks rather similar to #176 but is concerned with method references instead of lambda expressions.

Compiling the following example results in a StackOverflowException; stacktrace below.

```
#!java
import java.util.function.Supplier;

class MethodRefMinimal {
	public static <A> boolean search(A genericobj, Supplier<Boolean> pointer) { return false; }
	public static void main(String[] args) { boolean b = search(new Integer(), MethodRefMinimal::methodref); }
	public static Boolean methodref() { return true; }
}
```

Output and Stacktrace (first 40 frames):
```
Encountered error while processing testfiles/MethodRefMinimal.java
Fatal exception:
java.lang.StackOverflowError
        at java.util.HashMap.<init>(HashMap.java:467)
        at java.util.HashSet.<init>(HashSet.java:144)
        at org.extendj.ast.Constraints$ConstraintSet.<init>(Constraints.java:34)
        at org.extendj.ast.Constraints.addTypeVariable(Constraints.java:64)
        at org.extendj.ast.Expr.computeConstraints(Expr.java:126)
        at org.extendj.ast.Expr.inferTypeArguments_compute(Expr.java:1178)
        at org.extendj.ast.Expr.inferTypeArguments(Expr.java:1163)
        at org.extendj.ast.MethodAccess.potentiallyApplicable(MethodAccess.java:1560)
        at org.extendj.ast.MethodAccess.potentiallyApplicable(MethodAccess.java:120)
        at org.extendj.ast.MethodAccess.maxSpecific(MethodAccess.java:571)
        at org.extendj.ast.MethodAccess.decls_compute(MethodAccess.java:782)
        at org.extendj.ast.MethodAccess.decls(MethodAccess.java:770)
        at org.extendj.ast.MethodAccess.decl_compute(MethodAccess.java:823)
        at org.extendj.ast.MethodAccess.decl(MethodAccess.java:811)
        at org.extendj.ast.MethodAccess.Define_targetType(MethodAccess.java:2223)
        at org.extendj.ast.ASTNode.Define_targetType(ASTNode.java:3257)
        at org.extendj.ast.Expr.targetType(Expr.java:2097)
        at org.extendj.ast.MethodReference.targetInterface_compute(MethodReference.java:993)
        at org.extendj.ast.MethodReference.targetInterface(MethodReference.java:981)
        at org.extendj.ast.MethodReference.type_compute(MethodReference.java:801)
        at org.extendj.ast.MethodReference.type(MethodReference.java:785)
        at org.extendj.ast.Expr.computeConstraints(Expr.java:131)
        at org.extendj.ast.Expr.inferTypeArguments_compute(Expr.java:1178)
        at org.extendj.ast.Expr.inferTypeArguments(Expr.java:1163)
        at org.extendj.ast.MethodAccess.potentiallyApplicable(MethodAccess.java:1560)
        at org.extendj.ast.MethodAccess.potentiallyApplicable(MethodAccess.java:120)
        at org.extendj.ast.MethodAccess.maxSpecific(MethodAccess.java:571)
        at org.extendj.ast.MethodAccess.decls_compute(MethodAccess.java:782)
        at org.extendj.ast.MethodAccess.decls(MethodAccess.java:770)
        at org.extendj.ast.MethodAccess.decl_compute(MethodAccess.java:823)
        at org.extendj.ast.MethodAccess.decl(MethodAccess.java:811)
        at org.extendj.ast.MethodAccess.Define_targetType(MethodAccess.java:2223)
        at org.extendj.ast.ASTNode.Define_targetType(ASTNode.java:3257)
        at org.extendj.ast.Expr.targetType(Expr.java:2097)
        at org.extendj.ast.MethodReference.targetInterface_compute(MethodReference.java:993)
        at org.extendj.ast.MethodReference.targetInterface(MethodReference.java:981)
        at org.extendj.ast.MethodReference.type_compute(MethodReference.java:801)
        at org.extendj.ast.MethodReference.type(MethodReference.java:785)
        at org.extendj.ast.Expr.computeConstraints(Expr.java:131)
        at org.extendj.ast.Expr.inferTypeArguments_compute(Expr.java:1178)
```


Either of the following modification causes the code to compile:

* making the generic argument specific
* pass null as a value to the generic argument instead of `new Integer()`

## Comments

### Jesper Öqvist - 2017-02-03

It looks like type inference is being computed for the method reference, which is odd because `MethodRefMinimal::methodref` has a fixed type and there should be no need to do type inference on it.

Here is a similar test case which someone else sent to me:

```java
import java.util.List;
import java.util.function.Function;

public class Test {
  void m(List<String> list) {
    list.stream().map(String::length);
  }
}
```

The same error would however occur if the referenced method is to a generic method, as in the following test case:

```java
import java.util.List;

public class Test {
  <B> B convert() {
    return null;
  }

  void m2(List<Test> list) {
    list.stream().map(Test::convert);
  }
}
```

The right thing is probably to change the method reference type attribute into a circular attribute. Similar changes should probably be done to handle constructor references too.

### Jesper Öqvist - 2017-02-03

The way that the type of a method reference is defined in [JLS8 15.3.2](https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.13.2), it seems like the method reference target type always needs to be found. The following sentence makes this clear:

> If a method reference expression is compatible with a target type T, then the type of the expression, U, is the ground target type derived from T.

Consequently, I think that the correct thing to do is to make the method reference type attribute circular, just as in issue #176.

I've added a couple tests in the regression test suite for method and constructor references and the fix seems to be working nicely, so I'll commit it.

Thank you for reporting the bug @the-Other-One!

### Jesper Öqvist - 2017-02-03

Fix circularity in method reference type analysis

This fixes stack overflow errors in method and constructor reference type
analysis.  The errors occurred because the MethodReference.type() and
ConstructorReference.type() attributes were effectively circular in a when
method or constructor references were used in a type inference context, for
example as argument to a generic method.

fixes #180 (bitbucket)


→ <<cset a3af8c385f25>>
