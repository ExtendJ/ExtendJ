# Type variable positions not taken into account when comparing signatures

[JLS Version 7, chapter 8.4.2](http://docs.oracle.com/javase/specs/jls/se7/html/jls-8.html#jls-8.4.2) states:

     Two methods have the same signature if they have the same name and argument types.

     Two method or constructor declarations M and N have the same argument types if all of the following conditions hold:

    * They have the same number of formal parameters (possibly zero)

    * They have the same number of type parameters (possibly zero)

    * Let A1, ..., An be the type parameters of M and let B1, ..., Bn be the type parameters of N. After renaming each occurrence of a Bi in N's type to Ai, the bounds of corresponding type variables are the same, and the formal parameter types of M and N are the same.

In other words, when comparing two generic method signatures the position of the type variables must be taken into account when deciding if the signatures are the same or not. This doesn't seem to be done correctly right now in JastAddJ.

Three test cases:

```
#!java
class Test {
        interface Y { <S> void execute(S s); }
        interface X { <T> void execute(T s); }
        interface Exec extends Y, X { }

        class InncerClass implements Exec {
                //No problem, the methods are compatible
                public <A> void execute(A a) { }
        }
}
```

```
#!java
class Test {
        interface Y { <S, T> void execute(S s, T t); }
        interface X { <T, S> void execute(T t, S s); }
        interface Exec extends Y, X { }

        class InnerClass implements Exec {
                //No problem, the methods are compatible
                public <A, B> void execute(A a, B b) {

                }
        }
}

```

```
#!java
class Test {
        interface Y { <S, T> void execute(S s, T t); }
        interface X { <T, S> void execute(S S, T t); }
        interface Exec extends Y, X { }

        class InnerClass implements Exec {
                /*Should fail, this method does not override all methods in Exec
                  but has the same erasure*/
                public <A, B> void execute(A a, B b) { }
        }
}
```
