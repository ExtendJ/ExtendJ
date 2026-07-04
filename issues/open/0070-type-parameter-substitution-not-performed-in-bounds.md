# Type parameter substitution not performed in bounds

Currently, type parameter substitution does not seem to be done in bound lists for JastAddJ. Also, there is an attribute called usesTypeVariable which is only defined for normal methods and thus does not take type parameter bounds into consideration. Two test cases can be found below:


```
#!java
class Test {
        interface X<A> { <T extends A> void execute(int a); }

        public class InnerClass implements X<Integer> {
                /* Should fail, overrides the incorrect method.
                   Should override: <T extends Integer> void execute(int a)*/
                @Override
                public <T extends Number> void execute(int a) { }
        }
}
```

```
#!java

class Test {
        interface X<A> { <T extends A> void execute(int a); }

        public class InnerClass implements X<Number> {
                /* Should compile.*/
                @Override
                public <T extends Number> void execute(int a) { }
        }
}
```

A suggested fix for the usesTypeVariable attribute can be found below:

```
#!java

eq GenericMethodDecl.usesTypeVariable() {
        return super.usesTypeVariable() || getTypeParameterList().usesTypeVariable();
}
```
