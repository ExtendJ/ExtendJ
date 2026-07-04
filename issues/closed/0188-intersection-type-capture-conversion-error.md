# Intersection type capture conversion error

**Status:** resolved

*ExtendJ 8.0.1-154-gaf5cf06 Java SE 8*

The type of a conditional expression can be an intersection type if the two result expressions have different reference types with multiple common supertypes. In this case, ExtendJ fails to perform capture conversion to get the intersection type to match for example an assignment target type as in the following test case:

```java
// Test for error in ExtendJ's conditional expression type analysis.
// The type of a conditional expression with reference type result expressions
// is the intersection type of the common supertypes of the expression types.
// .result=COMPILE_PASS

interface Ownable { }
interface Thing { }
abstract class Entity implements Thing { }
class Unit extends Entity implements Ownable { }
class Resource extends Entity implements Ownable { }

class Test {
  void foo(Resource resource, Unit unit) {
    // Thing is a commmon ancestor type of Resource and Unit.
    Thing l = (resource != null) ? resource : unit;
  }
}
```

The test case gives the following error:

```
    [junit] Compilation failed when expected to pass:
    [junit] tests/type/conditional_expr_10p/Test.java:15: error: can not assign variable l of type Thing a value of type Entity & Ownable
```

The type of the conditional expression in the test case is the intersection type `Entity & Ownable`, and there should exist a capture conversion to `Thing`. The test case passes with javac.

## Comments

### Jesper Öqvist - 2017-04-10

Handle widening conversion for intersection types

This fixes an issue that caused some conditional expressions to
incorrectly generate type compatibility errors.  Conditional
expressions that had an intersection type as result type were not
handled in widening conversion.

fixes #188 (bitbucket)


→ <<cset 55723d2a4329>>
