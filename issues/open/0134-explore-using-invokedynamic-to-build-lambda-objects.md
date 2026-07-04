# Explore using Invokedynamic to build Lambda objects

**ExtendJ 8.0.1**

ExtendJ generates separate class files for each lambda in the program. This generates many extra files, and differs from the way javac handles lambdas. Javac uses invokedynamic and [LambdaMetaFactory](https://docs.oracle.com/javase/8/docs/api/java/lang/invoke/LambdaMetafactory.html) to create lambda objects. This removes the need for separate classfiles as the bytecode for each lambada is generated at runtime. This also makes the inner workings of lambda bytecode dependent on the JVM, with the bonus of benefiting from future performance improvements through the JVM.

Generating lambda bytecode using invokedynamic would require some big changes in the current lambda implementation, but it may be worth it to implement in the future.
