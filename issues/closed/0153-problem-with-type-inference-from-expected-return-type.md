# Problem with type inference from expected return type

**Status:** resolved

The following example code fails to compile:
```
#!Java
package test;

public class MinTestGeneric {

    public interface TransformFunctionFunction<O,N> {
        public N transform (O val);
    }

    public interface Host<T> {
        <R> Host<R> transformHost (TransformFunctionFunction<T,R> function);
    }

    public static class StringToIntegerFunction implements TransformFunctionFunction<String, Integer> {

        @Override
        public Integer transform(String val) {
            return val.length();
        }
    }

    public static class StringHost implements Host<String> {

        @Override
        public <R> Host<R> transformHost(TransformFunctionFunction<String, R> function) {
            return null;
        }
    }

    public static void main (String[] args) {
        Host<String> stringHost = new StringHost();

        Host<Integer> integerHost = stringHost.transformHost(new StringToIntegerFunction());

        System.out.println("Done");
    }
}
```

Error Message:
```
/Users/[...]/MinTestGeneric.java:32: error: no method named transformHost(test.MinTestGeneric.StringToIntegerFunction) in test.MinTestGeneric.Host<java.lang.String> matches. However, there is a method transformHost(test.MinTestGeneric.TransformFunctionFunction<java.lang.String, R>)
CompilationUnit PackageDecl="test"
```
Assuming that the compiler can figure out that `StringToIntegerFunction` subtype of `TransformFunctionFunction<String, Integer>` the issue seem to be that the `R` type parameter is not bound to `Integer` as the expected return type from the call would suggest.

The example is simplified snippet from one of the Apache Flink Java examples I toyed around with.

## Comments

### Jesper Öqvist - 2016-03-10

Thank you for the bug report! I was able to reproduce the bug.

Generic type inference is known to be a little buggy in ExtendJ, and I am not very familiar with the type inference in ExtendJ. There is a small constraint solving system used to infer type arguments, and if there is a bug in the constraint solver I might not be able to fix it. I will have a look at this bug and see if there is a simple fix.

### Jesper Öqvist - 2016-03-10

There are two problems here in ExtendJ:

* The type inference does not infer that the `R` type parameter should be `Integer`.
* ExtendJ does not treat `StringToIntegerFunction` as a subtype of `Host<String, R>`, which should be true even without type inference inferring `R` to be `Integer`.

### Jesper Öqvist - 2016-03-10

After running a debugger and looking at the method lookup, it seems like the type inference actually infers the method type parameter correctly but fails to substitute the host class type parameter. I get the following inferred argument type in the during method lookup: `Test.TransformFunction<T, java.lang.Integer>`. I don't know why the type printed in the error message is different right now.

Here is a smaller test case:

```java
// .result=COMPILE_PASS
public class Test {
  public interface C<T> {
    <R> void transform(I<T, R> f);
  }

  public interface I<O, N> {
  }

  class Impl implements I<String, Integer> {
  }

  void f(C<String> host, Impl fun) {
    host.transform(fun);
  }
}
```

### Jesper Öqvist - 2016-03-10

The problem seems to be caused by type lookup for the generic method first substituting the T type variable, then replacing the R type variable, independently.

### Jesper Öqvist - 2016-03-12

Improve type substitution

Method type parameters can now be substituted even if the host type has
substituted type parameters.

Method return type substitution has been unified with other type
substitutions: the inter-type method
TypeDecl.substituteReturnType(Parameterization) has been removed.  Type
substitution can now be done on TypeAccess, not only TypeDecl, using
the new inter-type method TypeAccess.substitute(Parameterization).

Improved handling of wildcard types during code generation (previously erased
during substitution).

fixes #153 (bitbucket)


→ <<cset d22808bf67b3>>
