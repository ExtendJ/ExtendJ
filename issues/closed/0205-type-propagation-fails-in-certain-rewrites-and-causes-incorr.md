# Type propagation fails in certain rewrites and causes incorrect AST disambiguation

**Status:** resolved

Extendj Version: Head, Commit: 8900768, Java SE 8

The following fails with the misleading message "package .o not found."

`((Demo.container) new container())` resolves to unknown type rather than `container`, which causes `resolveAmbiguousName` to decide `((Demo.container) new container()).o` is a `PackageAccess` rather than a `FieldAccess` (since `o` is not a known field in unknown type).

`Object it = ((List) ((Demo.container) body).o).iterator();` works just fine, as does further simplification of the RHS expr. (Original code comes from Soot.)

```java
import java.util.*;

public class Demo {
  static public class container { public List<Object> o = new ArrayList<>(); public container() {} }

  void foo() {
    container body = new container();
    Object it;
    it = ((List) ((Demo.container) body).o).iterator();
  }
}
```

## Comments

### Jesper Öqvist - 2017-08-25

Thank you for the detailed bug report! I'll take a look at it.

### Jesper Öqvist - 2017-08-31

This is a tricky issue. The problem is really that the current name resolution only considers chains of simple identifiers. When a more complex expression occurs on the left-hand side of a Dot expression with an unresolved ParseName as the right-hand side, then the ParseName rewrite does not inspect the type of the left-hand side during its rewrite process.

I constructed a more minimal test case for the issue:

```
public class Test {
  static public class Container {
    public String o = "x";
  }

  void foo(Container con) {
    ((String) ((Test.Container) con).o).length();
  }
}
```

A graph of the relevant part of the AST (problematic Dot highlighted):

![ast.png](https://bitbucket.org/repo/KAa5ga/images/2140639006-ast.png)

### Jesper Öqvist - 2017-08-31

> The problem is really that the current name resolution only considers chains of simple identifiers.

I spoke too soon. It's been a while since I worked with name resolution.

Debugging confirms that the type of the left-hand side of the inner dot is indeed Unkown type.

### Jesper Öqvist - 2017-08-31

A part of the problem seems to be that the rewrite of the right-hand side of the inner Dot is triggered before the rewrite of the left-hand side completes. This is problematic because the type attribute will then be evaluated on an unclassified expression which leads to the Unknown type.

A reason for the right-hand side rewrite interrupting the left-hand side rewrite is that the generated code uses `getRight()` instead of `getRightNoTransform()` in the inherited attribute evaluation:

```java
    if (getRightNoTransform() != null && _callerNode == getRight()) {
      // @declaredat /home/jesper/git/extendj/java4/frontend/LookupType.jrag:606
      return getLeft().qualifiedLookupType(name);
    }
    else {
      return getParent().Define_lookupType(this, _callerNode, name);
    }
```

The lookupType attribute is evaluated during resolvePackageOrTypeName while classifying the "Test.Container" name.

![Screenshot at 2017-08-31 14:28:09.png](https://bitbucket.org/repo/KAa5ga/images/1974350911-Screenshot%20at%202017-08-31%2014:28:09.png)

### Jesper Öqvist - 2017-09-12

A tentative solution to this issue is to introduce a new rewrite condition for the `ParseName` rewrite:

```diff
diff --git a/java4/frontend/ResolveAmbiguousNames.jrag b/java4/frontend/ResolveAmbiguousNames.jrag
index 03f5789..104285e 100644
--- a/java4/frontend/ResolveAmbiguousNames.jrag
+++ b/java4/frontend/ResolveAmbiguousNames.jrag
@@ -531,8 +531,30 @@ aspect NameResolution {
     return resolvePackageOrTypeName(qualifier, name);
   }

+  /**
+   * A parse name can only be disambiguated if it is not qualified by another
+   * unresolved parse name.
+   */
+  inh boolean ParseName.canResolve();
+
+  // Default equations:
+  eq TypeDecl.getChild().canResolve() = true;
+  eq BodyDecl.getChild().canResolve() = true;
+  eq CompilationUnit.getChild().canResolve() = true;
+
+  eq AbstractDot.getRight().canResolve() = !getLeft().containsParseName();
+
+  syn boolean Expr.containsParseName() = false;
+  eq Binary.containsParseName() =
+      getLeftOperand().containsParseName() || getRightOperand().containsParseName();
+  eq CastExpr.containsParseName() =
+      getTypeAccess().containsParseName() || getExpr().containsParseName();
+  eq ParExpr.containsParseName() = getExpr().containsParseName();
+  eq ParseName.containsParseName() = true;
+
   // This rewrite replaces a parsed name with a name reclassified according to context.
   rewrite ParseName {
+    when (canResolve())
     to Access {
       NamePart name = NamePart.EMPTY;
       switch (nameType()) {
```

This works by ensuring that the rewrites are ordered from left-to-right in qualified name expressions, so that the left-most part is resolved first.

### Jesper Öqvist - 2017-09-13

Fix error in ParseName resolution

A rewrite condition has been added to the ParseName rewrite to enforce
left-to-right rewrite ordering in qualified expressions. This fixes a case
where name resolution failed because a right hand side rewrite was triggered
prematurely by an inherited attribute evaluation.

The ParseName was effectively not monotonic, since it could get "stuck" in the
wrong state. This was a very rare occurrence, and only happened in certain cases,
similar to this expression tree:

((String) ((A.B) B).x).length()

The ParseName rewrite of A.B resulted in premature rewriting of x. The
ParseName x was then incorrectly resolved as a package name.

fixes #205 (bitbucket)


→ <<cset 7f47d0207dfa>>

### Jesper Öqvist - 2017-09-13

Thank you for submitting the issue! I had previously suspected that there might be a rewrite ordering issue in the ParseName rewrite, but I didn't have any concrete test cases and didn't have time to really look into the issue. It really helped to have a test for this!
