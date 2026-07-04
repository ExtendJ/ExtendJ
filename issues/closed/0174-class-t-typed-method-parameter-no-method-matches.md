# Class<T> typed method parameter - no method matches

**Status:** resolved

This example causes an error:

*NoMethodNameMatches01.java:7: error: no method named newE(java.lang.Class<D>) in NoMethodNameMatches01 matches. However, there is a method newE(java.lang.Class<T>)*
```
#!java
import java.io.Serializable;

public class NoMethodNameMatches01 implements EFactory {

	public static void main(String[] args) {
		NoMethodNameMatches01 n = new NoMethodNameMatches01();
		n.newE(D.class);
	}

	@Override
	public <KEY, T extends E<KEY>> T newE(Class<T> ct) {
		return null;
	}

}
interface EFactory {
    <K, T extends E<K>> T newE(Class<T> ct);
}
interface E<K> extends Serializable {}
interface D extends E<Long> {}

```

## Comments

### Jesper Öqvist - 2017-04-22

Thank you for submitting this issue Jörg!

The error is caused by a problem in the type analysis which causes the type argument for KEY to not be inferred correctly. The type inference infers the type Object for KEY, however it should be able to infer KEY=Long.

If KEY=Object is inferred, then the inferred type arguments (`<Object, D>`) are not within the type bounds and the method binding fails.

The test case you gave can be reduced a bit and still trigger the same error:

```java
// Test for type inference type variables inside type bounds.
// https://bitbucket.org/extendj/extendj/issues/174/class-typed-method-parameter-no-method
// .result=COMPILE_PASS

public class Test {
  void m() {
    newE(D.class);
  }

  <KEY, T extends E<KEY>> T newE(Class<T> ct) {
    return null;
  }
}

interface E<K> {}
interface D extends E<Long> {}
```

I have added this test to the test suite, and will try to see if the type inference can be improved so that it can resolve the type for KEY.

### Jesper Öqvist - 2017-04-22

I think the problem is caused by the `Constraints.constraintEqual()` implementation. An excerpt of the code (java5/frontend/GenericMethodsInference.jrag):

```java
      } else if (F instanceof TypeVariable) {
        if (typeVariables.contains(F)) {
          addEqualConstraint(F, A);
        }
     }
```

This if-statement looks like it should correspond to the following condition in [JLS 7 section 12.2.7, number 3, first bullet](https://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.12.2.7):

> If F = Tj, then the constraint Tj = A is implied.

However, the if-statement tests if F is a type variable, not if F is equal to Tj. It would require a large change to actually test for equality with Tj since the algorithm right now does not care about *which* type variable it is using the equality constraint for.

### Jesper Öqvist - 2017-04-22

After more investigation, I think that we are actually not adding enough constraints in the last step of type inference.

According to [JSL 7 section 15.12.2.8](https://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.12.2.8):

> additional constraints Bi[T1=B(T1) ... Tn=B(Tn)] >> Ti, where Bi is the declared bound of Ti,
>
> additional constraints B(Ti) << Bi[T1=B(T1) ... Tn=B(Tn)], where Bi is the declared bound of Ti,

These constraints don't appear to be added in ExtendJ.

The corresponding excerpt from java5/frontend/GenericMethodsInference.jrag:

```java
    if (constraints.unresolvedTypeArguments()) {
      ...
      constraints.convertibleFrom(S, R);
      constraints.resolveEqualityConstraints();
      constraints.resolveSupertypeConstraints();
      constraints.resolveSubtypeConstraints();
      // TODO(joqvist): missing constraints?
    }
```

I can't remember the reason I left that TODO comment, but it seems like I noticed this difference between the spec and implementation previously.

### Jesper Öqvist - 2017-04-22

Improve method type inference

Added missing constraints in the method type inference.

This fixes a type inference issue in test case generics/method_18p.

fixes #174 (bitbucket)


→ <<cset ed23d5053d17>>
