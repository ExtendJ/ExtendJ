# Members of raw interface types should also be raw

[JSL version 7, chapter 4.8](http://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.8) states:

     The type of a constructor (§8.8), instance method (§8.4, §9.4), or non-static field (§8.3) M of a raw type C that is not inherited from its superclasses or superinterfaces is the raw type that corresponds to the erasure of its type in the generic declaration corresponding to C.

In JastAddJ, the members of a raw interface do not seem to be erased properly. For example, see the test case below:


```
#!java

class Test {
        interface X<A> { <T> void execute(int a); }
        interface Y<B> { <S> void execute(int a); }
        interface Exec<A> extends Y<A>, X { }

        public class InnerClass implements X {
                /* Should fail, overrides the incorrect method.
                   Should override: void execute(int a) since
                 X is a raw type and its method should be erased. */
                @Override
                public <T> void execute(int a) {  }
        }
}
```
