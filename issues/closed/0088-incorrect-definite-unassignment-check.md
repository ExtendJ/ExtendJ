# Incorrect definite unassignment check

**Status:** resolved

**JastAddJ 7.1.1-263-g1cc8abc Java SE 7**

According to [JLSv3 16.1.7](http://docs.oracle.com/javase/specs/jls/se7/html/jls-16.html#jls-16.1.7), a variable V is definitely (un)assigned after a boolean expression when true or false if it is (un)assigned after the expression. There is a bug in JastAddJ where this is not correctly computed. For example, the following is accepted by JastAddJ despite a double assignment to final:

```
final int v;
if ((v=3)==2 || (v=2)==3) ;
```

The error is caused by the equation for definite assignment after true/false for boolean expressions. Diff for the fix is below:

```diff
-  eq Expr.isDUafterFalse(Variable v) {
-    if(isTrue())
-      return true;
-    return isDUbefore(v);
-  }
-  //= isFalse() && isDUbefore(v) || isTrue();
-  eq Expr.isDUafterTrue(Variable v) {
-    if(isFalse())
-      return true;
-    return isDUbefore(v);
-  }
+  // JLSv3 16.1.7
+  eq Expr.isDUafterFalse(Variable v) = isTrue() || isDUafter(v);
+  eq Expr.isDUafterTrue(Variable v) = isFalse() || isDUafter(v);
```

## Comments

### Jesper Öqvist - 2015-02-08

The definite assignement check after true/false looks correct as is.

### Jesper Öqvist - 2015-02-08

Actually, the definite assignedness after true/false checks look incorrect. Diff against what they probably should be:

```diff
-  eq Expr.isDAafterFalse(Variable v) = isTrue() || isDAbefore(v);
-  eq Expr.isDAafterTrue(Variable v) = isFalse() || isDAbefore(v);
+  eq Expr.isDAafterFalse(Variable v) = isTrue() || isDAafter(v);
+  eq Expr.isDAafterTrue(Variable v) = isFalse() || isDAafter(v);
```

This is difficult to find a test case to check.

### Jesper Öqvist - 2015-02-08

Applying the diff above gives lots of test errors. Creating a separate ticket for the assignedness case and letting this ticket be fixed by the original patch.

### Jesper Öqvist - 2015-02-08

Fixed definite unassignment after true/false error

fixes #88 (bitbucket)


→ <<cset 6b559f346ce7>>
