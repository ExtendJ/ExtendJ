# Implement Java 8 type inference

The type inference implemented in ExtendJ is based largely on the Java 5 specification. Essentially the same specification of type inference was used up to Java 7, however the Java 8 specification has [a new chapter dedicated to type inference](https://docs.oracle.com/javase/specs/jls/se8/html/jls-18.html) and the specification looks quite different on a high level from [the previous type inference specification](https://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.12.2.7).

The current type inference implementation in ExtendJ works mostly fine, but it might be a good idea to adapt the implementation to closely follow the Java 8 specification so that finding and fixing errors for Java 8 and above is easier.

Related issues that require improved type inference: #217, #204
