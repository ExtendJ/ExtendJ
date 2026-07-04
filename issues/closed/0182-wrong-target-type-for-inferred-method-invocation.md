# Wrong target type for inferred method invocation

**Status:** resolved

ExtendJ 8.0.1-142-ga3af8c3 Java SE 8 infers the wrong target type for an inferred method invocation. The following minimal test case demonstrates the error:

```java
interface Muffin { }

interface Oven {
  <T> T bake();
}

public class Test {
  void eat(Muffin m) { }

  void go(Oven oven) {
    // ExtendJ should infer the target type Muffin for oven.bake(),
    // however infers java.lang.Object.
    eat(oven.bake());
  }
}
```

The test passes with Javac (`--source 8`) but fails with ExtendJ.

Relevant sections from the JLS 7 and 8:

* JLS 7 [§15.12.2.8](https://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.12.2.8)
* JLS 8 [§18.5.2](https://docs.oracle.com/javase/specs/jls/se8/html/jls-18.html#jls-18.5.2)

Both JLS 7 and 8 seem to describe method invocation inference as using the target type of the method invocation to infer the type arguments when type inference on the actual arguments left unresolved type arguments, however they are vague about what happens if the context for the method invocation is another inferred method invocation. In ExtendJ this case would lead to a circular attribute dependency since the method lookup relies on type inference, and the type inference relies on method lookup to find the target type of an argument using type inference.

I'm not sure what the solution to the bug should be, but the relevant parts of ExtendJ are:

```java
// java5/frontend/GenericMethodsInference.jrag:
eq MethodAccess.getArg().assignConvertedType() = typeObject();
```

```java
// java5/frontend/GenericMethodsInference.jrag:
// in Expr.computeConstraints():
    if (constraints.unresolvedTypeArguments()) {
      TypeDecl S = assignConvertedType(); // = j.l.Object if in method argument
      if (S.isUnboxedPrimitive()) {
        S = S.boxed();
      }
      TypeDecl R = resultType;
      TypeDecl Rprime = R;
      if (R.isVoid()) {
        R = typeObject();
      }
      constraints.convertibleFrom(S, R);
      constraints.resolveEqualityConstraints();
      constraints.resolveSupertypeConstraints();
      constraints.resolveSubtypeConstraints();
    }
```

## Comments

### Jesper Öqvist - 2017-02-04

The test case fails with JDK 7, and compiles in JDK 8. It seems ExtendJ just needs to handle updated inference rules in Java 8 in this case.

Here is the output from javac 1.7.0_121:

```
tests/generics/inference_09p/Test.java:19: error: method eat in class Test cannot be applied to given types;
    eat(oven.bake());
    ^
  required: Muffin
  found: Object
  reason: actual argument Object cannot be converted to Muffin by method invocation conversion
1 error
```

### Jesper Öqvist - 2017-04-21

There is a comment about target type inference being done in a separate "round", to allow target type to influence inference without affecting potentially applicable methods. This seems like a typical workaround for a circular dependency issue. The full quote from the [JLS 8, §18.5.2](https://docs.oracle.com/javase/specs/jls/se8/html/jls-18.html#jls-18.5.2):

>It is important to note that two "rounds" of inference are involved in finding the type of a method invocation. This is necessary to allow a target type to influence the type of the invocation without allowing it to influence the choice of an applicable method. The first round produces a bound set and tests that a resolution exists, but does not commit to that resolution. The second round reduces additional constraints and then performs a second resolution, this time "for real".

I found a solution to the problem in ExtendJ by using a NTA to pin a potentially applicable method while doing type inference using its arguments.

### Jesper Öqvist - 2017-04-22

Improve method type inference for Java 8

Method type inference in Java 8 added inference of the return type, which in a
naive implementation leads to circular type inference when the inferred method
access is inside another inferred method access.

To solve this issue, the type inference for methods has been changed in the
Java 8 extension so that a NTA bound method access is used to perform type
inference.  This effectively pins a method access to a potentially applicable
method so that type inference can be performed on its arguments without
circularly invoking type inference on the outer method access.

This fix is important as many useful methods in the Java 8 standard library
rely the improved method type inference.  For example, the excerpt will now
compile:

	Stream<Integer> stream = ...;
    stream.collect(Collectors.toList());

fixes #182 (bitbucket)


→ <<cset 80e3c13fe890>>
