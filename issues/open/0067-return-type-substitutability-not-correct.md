# Return type substitutability not correct

[JSR 335, Part A section 8.4.5](http://cr.openjdk.java.net/~dlsmith/jsr335-pfd/spec/A.html#A8.4.5) is a bug fix for the JLS, and implies that it is required to take the type variables into account when computing return type substitutability also pre-Java 8 even though it was not stated explicitly in the older JLS. This is not currently done in JastAddJ.

Also, for primitive types, they have to be exactly the same to be return type substitutable, but JastAddJ currently only checks subtype, which means for example int will be compatible with long. Test cases follow below:


```
#!java
class Test {
        interface X { int execute(int a); }
        interface Y { long execute(int a); }

        //Should fail, int and long are not return type substitutable
        interface Exec extends Y, X { }
}
```


```
#!java

public class Test {
        public <S, T> Object someMethod() {
                class InnerClass<B> {
                        public <I, J> J someOtherMethod() {
                                return null;
                        }
                }

                class InnerClass2<V> extends InnerClass<V> {
                        /*Should fail, not return type substitutable
                          with someOtherMethod in InnerClass*/
                        public <I, J> I someOtherMethod() {
                                return null;
                        }
                }
		Object o = new InnerClass<Integer>();
		return o;
        }
}
```
