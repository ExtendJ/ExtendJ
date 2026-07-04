# Unnecessary CHECKCAST instructions

**Status:** resolved

*ExtendJ 8.0.1-229-g3f4f983 Java SE 8*

ExtendJ in some cases generates unnecessary checkcast instructions. An obvious case of an unnecessary checkcast is in the generated code for the test case `generics/static_04p`. The relevant part of the code is:

```java
class Container<T> {
  T value;
}

class Helper {
  static <T> T valueOf(Container<? extends T> c) {
    return c.value;
  }
}
```

ExtendJ generates the following bytecode for `Helper.valueOf()`:

```
  static <T extends java/lang/Object> T valueOf(Container<? extends T>) throws ;
    Code:
       0: aload_0
       1: getfield      #24                 // Field Container.value:Ljava/lang/Object;
       4: checkcast     #4                  // class java/lang/Object
       7: checkcast     #4                  // class java/lang/Object
      10: areturn
```

Both checkcast instructions are redundant because the result already has type `Object`. When compiled with javac, no checkcast instructions are used.

Expected result: there should be no checkcast instructions generated.

Actual result: ExtendJ generates two redundant checkcast instructions.

## Comments

### Jesper Öqvist - 2017-12-13

Improve checkcast generation

Fixed several cases where redundant checkcast instructions could be generated.
This is done by moving the casting operation from where the expression value is
evaluated, to the context where it is used.

Also fixed incorrect casts by using the erased source type to decide if a cast
is needed.

Future work: look over casts in AssignExpr.createBCode, Unary.emitPostfix, and
Unary.emitPrefix.

fixes #230 (bitbucket)
fixes #229 (bitbucket)


→ <<cset b654375798b4>>

### Jesper Öqvist - 2017-12-13

Reverted the fix for this temporarily.

### Jesper Öqvist - 2017-12-19

Redesign typecasting code generation

Removed casting conversions directly after method invocation and field loading.
Instead, casting is now handled in the context where a field or method is used.

Added method Expr.emitCastTo(_, _) to handle type conversion from an expression
to an expected type.

Added method Expr.emitAssignConvTo(_, _) to handle assignment conversion from
an expression to an expected type.

Added attribute Expr.erasedType(), for computing the erased runtime type of an
expression.

fixes #229 (bitbucket)
fixes #230 (bitbucket)


→ <<cset 738074c49cbc>>
