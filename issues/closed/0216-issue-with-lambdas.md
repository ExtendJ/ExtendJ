# Issue with lambdas

**Status:** resolved

When using a lambda, like in the following code :

```
#!Java
public class MainTest {
	public static void main(String[] args) {
		A[] arr = new A[] { new A() };

		System.out.println((Integer[]) java.util.Arrays.stream(arr)
				.map(a -> a.getInt(1))
				.collect(java.util.stream.Collectors.toList())
				.toArray(new Integer[arr.length]));
	}
}

class A {
	public int getInt(int a) { return a; }
}
```
the compiled binary crashes with the following Exception:

```
#!Java

Exception in thread "main" java.lang.AbstractMethodError: Method MainTest$1.apply(Ljava/lang/Object;)Ljava/lang/Object; is abstract
	at MainTest$1.apply(MainTest.java)
	at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:193)
	at java.util.Spliterators$ArraySpliterator.forEachRemaining(Spliterators.java:948)
	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:481)
	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)
	at java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:708)
	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
	at java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:499)
	at MainTest.main(MainTest.java:5)
```

## Comments

### Jesper Öqvist - 2017-11-21

ExtendJ generates an anonymous class to implement the lambda expression. In this case, ExtendJ infers the type of the lambda to be `Function<? super A, ? extends Object>`, and then it generates a class implementing this type. However, it is not allowed to have wildcards as type arguments.

Here is a smaller version of your test:

```java
import java.util.*;
import java.util.stream.Collectors;

public class L1 {
  public static void main(String[] args) {
    Collection<Integer> e = new LinkedList<>();
    e.add(1);
    e.add(2);

    e.stream()
        .map(a -> a)
        .collect(Collectors.toList());
  }
}
```

The bytecode for the implicitly generated anonymous lambda class:

```
Compiled from "L1.java"
class L1$1 implements java.util.function.Function<? super java.lang.Integer, ? extends java.lang.Object> {
  L1$1() throws ;
    Code:
       0: aload_0
       1: invokespecial #12                 // Method java/lang/Object."<init>":()V
       4: return

  public java.lang.Object apply(java.lang.Integer) throws ;
    Code:
       0: aload_1
       1: areturn
}
```

It is surprising that this was already not covered by the lambda tests in ExtendJ's regression test suite, but I'll add this test now.

### Jesper Öqvist - 2017-11-22

A simpler test that has the same problem:

```java
import java.util.function.Function;

public class Test {
  public static void main(String[] args) {
    map(1, e->e);
  }

  static <T, R> R map(T e, Function<? super T, R> f) {
    return f.apply(e);
  }
}
```

### Jesper Öqvist - 2017-11-22

Even simpler test:

```java
import java.util.function.Consumer;

public class L3 {
  public static void main(String[] args) {
    accept("x", e->{});
  }

  static <T> void accept(T v, Consumer<? super T> f) {
    f.accept(v);
  }
}
```

### Jesper Öqvist - 2017-11-22

Fix error in anonymous lambda class generation

The anonymous class implementing a lambda expression could have an illegal
declaration when the lambda had a parameterized type if the parameterized type
used wildcards. Wildcards are not allowed in a standalone type declaration, so
the non-wildcard parameterization of the type should be used instead.

fixes #216


→ <<cset 51f94952f3c4>>

### Jesper Öqvist - 2017-12-11

Removing milestone: java8 (automated comment)
