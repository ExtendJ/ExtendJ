# MethodAccess lacks definite un-assignedness attributes

**Status:** resolved

[JLS version 7, Chapter 16.1.18](http://docs.oracle.com/javase/specs/jls/se7/html/jls-16.html#jls-16.1.10) states:

     If an expression is not a boolean constant expression, and is not a preincrement expression ++a, predecrement expression --a, postincrement expression a++, postdecrement expression a--, logical complement expression !a, conditional-and expression a && b, conditional-or expression a || b, conditional expression a ? b : c, or assignment expression, then the following rules apply:
     * If the expression has subexpressions, V is [un]assigned after the expression iff V is [un]assigned after its rightmost immediate subexpression.
     For any immediate subexpression y of an expression x, V is [un]assigned before y iff one of the following situations is true:
     * x is a primary method invocation expression, y is the first argument expression in the method invocation expression, and V is [un]assigned after the primary expression that computes the target object.
     * x is a method invocation expression or a class instance creation expression; y is an argument expression, but not the first; and V is [un]assigned after the argument expression to the left of y.

But for MethodAccesses, only the definitely assigned attributes are defined, not the definitely unassigned ones.

Proposed fix:

     //Bug in java 4 implementation, only DA-related functions are specified for methods
	eq MethodAccess.getArg(int i).isDUbefore(Variable v) = computeDUbefore(i, v);
	syn lazy boolean MethodAccess.computeDUbefore(int i, Variable v) = i == 0 ? isDUbefore(v) : getArg(i-1).isDUafter(v);
    eq MethodAccess.isDUafter(Variable v) = getNumArg() == 0 ? isDUbefore(v) : getArg(getNumArg()-1).isDUafter(v);
    eq MethodAccess.isDUafterTrue(Variable v) =  (getNumArg() == 0 ? isDUbefore(v) : getArg(getNumArg()-1).isDUafter(v)) || isFalse();
    eq MethodAccess.isDUafterFalse(Variable v) =  (getNumArg() == 0 ? isDUbefore(v) : getArg(getNumArg()-1).isDUafter(v)) || isTrue();

## Comments

### Erik Hogeman - 2014-02-24

Test case for this bug:


```
#!java

public static void foo(int a) {

}

public static void main(String[] args) {
	final int a;
	boolean b = args[0].length() == 1;
	foo(b ? 3 : (a = 5));
	a = 4; //Should fail, a is not definitely unassigned here
 }
```

### Jesper Öqvist - 2014-02-24

Added test as `expr/definite_assignment04` in the regression test suite.

### Jesper Öqvist - 2014-02-24

Fixed definite assignment analysis problem

Fixed definite assignment analysis problem for method accesses.

fixes issue #52 (bitbucket)


→ <<cset aaccbebdd778>>
