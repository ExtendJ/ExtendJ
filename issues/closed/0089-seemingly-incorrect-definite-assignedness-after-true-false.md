# Seemingly incorrect definite assignedness after true/false

**Status:** resolved

The equations for definite assignedness after true/false seem incorrect when comparing to the specification in [JLSv3 16.1.7](http://docs.oracle.com/javase/specs/jls/se7/html/jls-16.html#jls-16.1.7):

Actually, the definite assignedness after true/false checks look incorrect. Diff against what they probably should be:

```diff
-  eq Expr.isDAafterFalse(Variable v) = isTrue() || isDAbefore(v);
-  eq Expr.isDAafterTrue(Variable v) = isFalse() || isDAbefore(v);
+  eq Expr.isDAafterFalse(Variable v) = isTrue() || isDAafter(v);
+  eq Expr.isDAafterTrue(Variable v) = isFalse() || isDAafter(v);
```

This is difficult to find a test case to check, and applying the patch above causes lots of regressions.

## Comments

### Jesper Öqvist - 2015-02-10

There are equations for `Binary` which provide the correct value in most cases,

```
  syn lazy boolean Binary.isDAafterTrue(Variable v) = isFalse() || getRightOperand().isDAafter(v);
  syn lazy boolean Binary.isDAafterFalse(Variable v) = isTrue() || getRightOperand().isDAafter(v);
```

however `CastExpr` is a subtype of `Expr` but not `Binary` so it is not covered by the correct expressions so I was able to create a test case using this info:

```
// .result=COMPILE_PASS
class Test {
        {
                int x;
                int y = 3;
                if (((boolean) (false || ((x=y)==2))) || true) ;
                int z = x; // x is definitely assigned here
        }
}
```

This case finally exposes a bug in the equations. However, the question is why I get regressions when I apply the fix.

### Jesper Öqvist - 2015-02-10

Test errors caused by stack overflows. Some incorrect circular evaluation in the DAafterFalse attribute.

### Jesper Öqvist - 2015-02-10

The problem was an additional error in `Expr.isDAafter`:

```diff
-  eq Expr.isDAafter(Variable v) = (isDAafterFalse(v) && isDAafterTrue(v)) || isDAbefore(v);
+  eq Expr.isDAafter(Variable v) = isDAbefore(v);
```

This caused for example integer literals to incite an infinite recursive call chain.

### Jesper Öqvist - 2015-02-10

Fixed incorrect definite assignment equations

fixes #89 (bitbucket)


→ <<cset e381205a8904>>
