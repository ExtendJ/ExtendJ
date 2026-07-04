# Type variable in another type variable's bound list do not prevent more bounds

[JLS version 7, chapter 4.4](http://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.4) states:

     Every type variable declared as a type parameter has a bound. If no bound is declared for a type variable, Object is assumed. If a bound is declared, it consists of either:

     * a single type variable T, or

     * a class or interface type T possibly followed by interface types I1 & ... & In.

Currently, JastAddJ allows a bound list to contain both a type variable and several interface types. A test case follows below:


```
#!java
import java.io.Serializable;
class Test {
        interface X<A> {
                /*Should fail, cannot use additional bound list
                    when first bound is a type variable */
                <T extends A & Serializable> void execute(int a);
        }
}
```
