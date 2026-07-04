# IntegerLiteral generates bad bytecode when constructed by the IntegerLiteral constructors

**Status:** resolved

ExtendJ 8.0.1 Java SE 8

When creating a nta with a new IntegerLiteral correct code is not generated.

In PQLCreateBCode.jrag:

```java
aspect PQLCreateBCode {
    public void QueryQuantExpr.createBCode(CodeGeneration gen) {
        toStream().createBCode(gen);
    }
}
```

In PQL.jrag:

```java
aspect PQL {
  syn nta Expr QueryQuantExpr.toStream() = new IntegerLiteral(5);
}
```

In program:

```java
public class PQL {
	public static void main(String[] args) {
                int c = query(Set.contains(x)):x;
		System.out.println("Integer: " + c);

	}
}
```

Expected result: Should print 5.

Actual result: Prints 0.

## Comments

### Jesper Öqvist - 2017-11-27

The IntegerLiteral constructors should work as you would expect them to, so this is an obvious error that should be fixed ASAP.

A workaround for this issue is to use `Literal.buildIntegerLiteral(int)` to construct an IntegerLiteral.

### Jesper Öqvist - 2017-11-27

This also affects other numeric literal types.

### Jesper Öqvist - 2017-11-28

Added test for this issue: `api/intlit_01p`

### Jesper Öqvist - 2017-11-28

Rewrite Java 7 numeric literal handling

* Changed NumericLiteral from an AST type into an interface that
  IntegerLiteral and LongLiteral implement.
* Removed the rewrite that transformed NumericLiteral into the
  different kinds of numeric literals.
* Factored out the unary minus rewrites to separate aspect in the
  Java 4 module to avoid code duplication.
* Fixed error causing numeric literal constructors to not fully initialize
  the constructed nodes.

fixes #218 (bitbucket)


→ <<cset 4bc40f3f336e>>
