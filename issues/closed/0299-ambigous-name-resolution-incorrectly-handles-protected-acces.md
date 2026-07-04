# Ambigous name resolution incorrectly handles protected access control

**Status:** resolved

JLS is rather opaque on this issue but the relevant bit is in JLS $6.6.2.1.
Specifically:  " If the access is by a field access expression E.Id ....  where E is a Primary expression (§15.8), then the access is permitted if and only if the type of E is S or a subclass of S.  "

Extendj's current impl of this  is in `Expr.mayAccess(Variable f)` and the (unused elsewhere and rather misleadingly implemented) helper `TypeDecl.mayAccess(Expr expr, Variable field)`.

The problem is that relevant part `TypeDecl.mayAccess(Expr expr, Variable field)`'s implementation for protected a check for `expr.type().instanceOf(this)`, but `expr` in this context during ambiguous name resolution is the `ParseName` that's being rewritten, not the expr-resolved-so-far.

`ParseName` does not have a known type because it isn't classified. The line in question is :
`SimpleSet<Variable> var = keepAccessibleFields(hostType.memberFields(name.toString()));`
in `ParseName.resolveAmbiguousName(NamePart qualifier, NamePart name)`.
Note the lack of qualifier on `keepAccessibleFields`, which in turn uses `Expr.mayAccess(Variable f)`.

Because of sheer dumb luck this is correct so long as the `hostType` that access a protected member is declared in the same package as the protected member's declaring class. If it isn't then it fails, because `ParseName.type()` is unknown and not declared in the same pkg.

To do this correctly the full access qualifier must be built incrementally while resolving, instead of all at once after attempting to guess the correct usage. This implies that `NamePart`'s subtypes have to go since we're building the actual accessors incrementally.

Test Code:

`package pkg0;
class Base { protected Base[] foo = new Base[5]; }

package pkg1;
import pkg0.*;
class Impl extends Base { int bar(Impl arg) { return arg.foo.length; } }
`

Expected result: Access to dependent member `foo` declared in class `Base` is permitted for code hosted in class `Impl`.

Actual result: Access denied.


MISC NOTE: `TypeDecl.mayAccess(Expr expr, Variable field)` is particularly dangerous since it is only correct when used via `Expr.mayAccess(Variable f)`. While it is not used anywhere else, I do feel it should be folded into `Expr.mayAccess(Variable f)`.

MISC NOTE: `TypeDecl.mayAccess(Expr expr, Variable field)`'s implementation is odd, and I'm not certain what rules it is attempting to implement other than 6.6.2.1.  (In particular the super-access and is-not-an-instance-field bit.)


**IMPORTANT PATCH NOTE:**  The patch inlines `TypeDecl.mayAccess(Expr expr, Variable field)` into `Expr.mayAccess(Variable f)` and drops the (spurious?) super-accessor check and instance field check. I'd appreciate if someone could double check that this is correct. If it isn't then some comment would be nice regarding which part of the JLS it implements.

## Comments

### Jesper Öqvist - 2018-01-31

Thank you for the detailed issue and patch! I need to look more at this, but here are my initial comments:

* I agree that it seems like `TypeDecl.mayAccess(Expr, Variable)` should be inlined. In the Git blame history I can see that I was the one who pointlessly moved the code out from `TypeDecl.mayAccess(Variable)` [six years ago](https://bitbucket.org/extendj/extendj/commits/373ac7acc652baf07186f2ceff25d5a7d8d6bd8d). I'm not sure why I did this, but it was done while fixing [this issue](https://svn.cs.lth.se/trac/jastadd-trac/ticket/52).
* You can submit this patch as a pull request if you want to. That way, the commit will be attributed to you in the git history. If you do that, please follow our [code style guide](http://extendj.org/code_style.html) and try to not make nonfunctional/unrelated changes.
* Formatting tip for BitBucket: use three backticks to start and end a code block:

```
  ```
    like this
  ```
```

### Jesper Öqvist - 2018-01-31

There is an error in your supplied test case. `Base` needs to be public:

```
Test.java:5: error: Base is not public in pkg0; cannot be accessed from outside package
 class Test extends Base {
```

After fixing this error the test case reproduces the issue.

### Jesper Öqvist - 2018-01-31

I applied your patch to [the latest commit in master](ff36116a02639bf75f622bb41ee5a0678d195297) and this results in 1022 regression test failures. The test case you provided fails with this message:

```
    [junit] [FAIL] runTest[name/access_03p](tests.extendj.TestJava7)
    [junit] Compilation failed when expected to pass:
    [junit] tests/name/access_03p/pkg0/Base.java:4: error: no type named Unknown
    [junit] tests/name/access_03p/pkg0/Base.java:4,13: error: no visible type named org.extendj.ast.NamePart@91161c7
    [junit] tests/name/access_03p/pkg0/Base.java:4: error: no type named Unknown
    [junit] tests/name/access_03p/pkg0/Base.java:4,30: error: no visible type named org.extendj.ast.NamePart@548e7350
    [junit] tests/name/access_03p/pkg1/Test.java:3,8: error: package org.extendj.ast.NamePart@e45f292 not found
    [junit] tests/name/access_03p/pkg1/Test.java:5,20: error: no visible type named org.extendj.ast.NamePart@3f49dace
    [junit] tests/name/access_03p/pkg1/Test.java:6,11: error: no visible type named org.extendj.ast.NamePart@6e1567f1
    [junit] tests/name/access_03p/pkg1/Test.java:7: error: return value must be an instance of int which Unknown is not
    [junit] tests/name/access_03p/pkg1/Test.java:7,12: error: package org.extendj.ast.NamePart@13805618.org.extendj.ast.NamePart@56ef9176 not found
    [junit] tests/name/access_03p/pkg1/Test.java:7,20: error: no field named org.extendj.ast.NamePart@4566e5bd is accessible
```

I added your test to the regression test suite as [`name/access_03p`](https://bitbucket.org/extendj/regression-tests/src/f979b7b0c94afcbf7924fec0b3a304e90e7ddce1/tests/name/access_03p/?at=master).

### Jesper Öqvist - 2018-01-31

If I keep only the changes you made in `LookupVariable.jrag`, the new test passes and all regression tests pass. If I include your changes in ambiguous name resolution I get lots of regressions.

I have still not read the code thoroughly, but at a glance the changes in `LookupVariable.jrag` look good and are clearer than the original code.

### Jesper Öqvist - 2018-01-31

I have now carefully read your changes in `Expr.mayAccess(Variable)`, and the previous implementation(s). The only functional change that you made in `Expr.mayAccess(Variable)` is the removal of this if-statement:

```
      if (!field.isInstanceVariable() || expr.isSuperAccess() || expr.type().instanceOf(this)) {
        return true;
      }
```

To test that this is true, try this patch on the latest commit from master (ff36116):

```diff
diff --git a/java4/frontend/LookupVariable.jrag b/java4/frontend/LookupVariable.jrag
index cb7a446..8cedeb2 100644
--- a/java4/frontend/LookupVariable.jrag
+++ b/java4/frontend/LookupVariable.jrag
@@ -330,9 +330,7 @@ aspect VariableScope {
    */
   public boolean TypeDecl.mayAccess(Expr expr, Variable field) {
     if (instanceOf(field.hostType())) {
-      if (!field.isInstanceVariable() || expr.isSuperAccess() || expr.type().instanceOf(this)) {
-        return true;
-      }
+      return true;
     }
     if (isNestedType()) {
       return enclosingType().mayAccess(expr, field);
```

With this change all regression tests pass and your new test also passes.

I do like your non-functional changes in changing the recursive type hierarchy check into a for-loop. Also, you simplified the control flow by merging the common case of a protected and package-private access.

I think I can explain now why the field is tested to be an instance variable. I think this came from a misreading of the [Java 6 specification §6.6.2.1](https://docs.oracle.com/javase/specs/jls/se6/html/names.html#6.6.2.1), specifically the sentence "In addition, if Id denotes an instance field or instance method, then: [...]". This was probably thought to be a necessary condition, hence the test `isInstanceVariable()`. The `isSuperAccess()` is harder to explain. It might have been added to fix to a particular compilation error. The test `expr.type().instanceOf(this)` was an attempt to fulfill this requirement in the specification: "If the access is by a field access expression E.Id, where E is a Primary expression, or by a method invocation expression E.Id(. . .), where E is a Primary expression, then the access is permitted if and only if the type of E is S or a subclass of S.". However, this condition is superfluous because the name lookup for the field will only bind to a field in a subtype of S.

The latest version of `Expr.mayAccess(Variable)` before I changed anything is [this version](https://bitbucket.org/extendj/extendj/src/43ae1c11a5068f1acf4fa0a0820dc805dc5bd6af/Java1.4Frontend/LookupVariable.jrag?at=accessor-ntas&fileviewer=file-view-default#LookupVariable.jrag-189) by Torbjörn Ekman:

```java
  public boolean Expr.mayAccess(FieldDeclaration f) {
    if(f.isPublic())
      return true;
    else if(f.isProtected()) {
      if(f.hostPackage().equals(hostPackage()))
        return true;
      TypeDecl C = f.hostType();
      TypeDecl S = hostType().subclassWithinBody(C);
      TypeDecl Q = type();
      if(S == null)
        return false;
      if(f.isInstanceVariable() && !isSuperAccess())
        return Q.instanceOf(S);
      return true;
    }
    else if(f.isPrivate())
      return f.hostType().topLevelType() == hostType().topLevelType();
    else
      return f.hostPackage().equals(hostType().hostPackage());
  }
```

Note that `FieldDeclaration` was changed to `Variable` as part of a later refactoring. The `TypeDecl.subclassWithinBody(TypeDecl)` method was removed at some point, likely because it became unused after I refactored that part of `Expr.mayAccess(FieldDeclaration)` into a separate method. The call to `hostType().subclassWithinBody(C)` returned the first enclosing type in the type hierarchy of the host type that was a subtype of `C`. The `if (S == null)` did exactly what the for-loop in your patch does.

As you can see the tests if the field is an instance variable and if the qualifying expression is a super access are there in Torbjörn's version. I later moved the tests before the supertype checks, assuming that it would be more efficient to do those checks first before iterating through the type hierarchy.

I only briefly looked at your changes in `ResolveAmbiguousNames.jrag`, but they seem unrelated for this issue.

### Olivier Hamel - 2018-01-31

I must confess some uncertainty if it case that the last condition ("if and only if the type of E is S or a subclass of S") holds true without explicit verification.

Actually given careful re-reading it appears we're both mistaken? The current approach works for static fields/members, but consider:

```java


package pkg0;
public class C {
  protected static int static_field = 5;
  protected        int instance_field = 0;
}

package pkg1;
import pkg0.C;

public class E extends C { }

package pkg2;
import pkg0.C;
import pkg1.E;

class S extends  C {
  void foo(E e) {
    int t0 = e.static_field;    // OK:  `static_field` declared in `C`, `S` sub-type of `C`
    int t1 = e.instance_field;  // ERROR:  `instance_field` declared in `C`, `instance_field` is an instance field
    // 6.6.2.1 says:
    //       `S` must be sub-type of `C` (oddly enough it does *not* say that classes in the same pkg are permitted; omission?)
    //      "In addition, if Id denotes an instance field or instance method" with qualifier `e :  E` then `E` must be subtype of `S`.
    //                                                                                                        fail this bit ^
  }
}
```

What follows is what I had planned to post before thinking of this counter example:


The patch does contain a bug, namely it is missing `public String toString() { return name; }` for `NamePart`. I accidentally chopped that off while doing a quick cleanup.
Predictably, this completely screws things over when it starts looking for a field/method/type/pkg named something like `org.extendj.ast.NamePart@xxxx` instead of the symbol contained within.

The changes in `ResolveAmbiguousNames.jrag` are relevant to implement the rule " .... access is permitted if and only if the type of E is S or a subclass of S ". Specifically, the original code was something along the lines of:

```java

protected Access ParseName.resolveAmbiguousName(Access qualifier, NamePart name) {
    if (qualifier == null) {
      ...
    } else {
      TypeDecl qualType = qualifier.type();
      if (qualType != null) {
        SimpleSet<Variable> var = keepAccessibleFields(qualType.memberFields(name.toString()));    //  <--- here is the problem, we need to qualify `keepAccessibleFields`
        if (!var.isEmpty()) return mkVarName(qualifier, name);
      }
    }

    return resolvePackageOrTypeName(qualifier, name);
  }
```

`this` in this context is `ParseName`, which is an `Access`. If you ask `ParseName` what its `type` is it'll stare at you cluelessly and say `unknownType()`. This is why there are large changes in `ResolveAmbiguousNames.jrag`, we needed an actual access expr setup for querying the type and obtaining `E`. (Such that in `Type.mayAccess`, we get a sensible value for `expr.type().instanceOf(this)`.) It seemed far more trouble to fake it as was done previously using the `NamePart`s round-about approach of assembling pseudo-accessors than to simply assemble the true access sequence on the fly and reuse the standard logic/attributes defined for lookups/checks.

The changes in `TypeDecl.mayAccess(Expr expr, Variable field)` & `Expr.mayAccess(...)`  were mainly there because `TypeDecl.mayAccess(...)` felt like a hand-grenade. It doesn't do what the name purports and `Type.mayAccess` will *not* evaluate to the same result as `Expr.mayAccess` given the same set of inputs. In fact, you can't correctly use it at all except through `Expr.mayAccess`. The later changes (iterative instead of recursive) where mechanical, and the removal of the other conditions due to them being suspect.

### Olivier Hamel - 2018-01-31

Patch removed, it's a buggy mess. It assumed that dots were permitted to be `foldl`, however they must be `foldr`.

### Jesper Öqvist - 2018-02-01

>    //       `S` must be sub-type of `C` (oddly enough it does *not* say that classes in the same pkg are permitted; omission?)

The Java 6 specification (and similarly in the later specs) handles the case that all protected field accesses are permitted within the same package with this clause in §6.6.1:

>Otherwise, if the member or constructor is declared protected, then access is permitted only when one of the following is true:
>
>* Access to the member or constructor occurs from within the package containing the class in which the protected member or constructor is declared.
>* ...

I see now why you changed the name resolution. It became apparent when I tried to fix your version of `mayAccess` so that it worked for that new test case (with S, E, and C).

This is my interpretation of the Java specification:

An access to a protected field declared in a class C is allowed from a class S if:

* `S` is in the same package as `C`, or,
* `S <: C` and if the field is an instance variable, the access is qualified by a type `Q` such that `Q <: S`.

Note that "from class S" is not a precise definition. It can be interpreted as either the directly enclosing class of the field access, or an outer class of the access. Indeed, all enclosing classes S should be checked and if any fulfills these conditions then the access is allowed. This is covered by our regression tests already, and handled by the fact that we iterate (or recurse) over enclosing types.

I updated your version of `mayAccess` to this:

```java
  /**
   * Test if this expression may access the given field.
   *
   * @see <a href="https://docs.oracle.com/javase/specs/jls/se6/html/names.html#6.6.1">JLS6 §6.6.1</a>
   * @return true if the expression may access the given field
   */
  syn boolean Expr.mayAccess(Variable f) = hostType().mayAccess(type(), f);

  /**
   * Test if a qualified field access may access the given field from this type.
   *
   * @param qualifier the type of the qualifying expression.
   * @param field the field being accessed.
   * @see <a href="https://docs.oracle.com/javase/specs/jls/se6/html/names.html#6.6.1">JLS6 §6.6.1</a>
   * @return true if the expression may access the given field
   */
  syn boolean TypeDecl.mayAccess(TypeDecl qualifier, Variable field) {
    if (field.isPublic()) {
      return true;
    }
    if (field.isPrivate()) {
      return field.hostType().topLevelType() == hostType().topLevelType();
    }

    if (field.hostPackage().equals(hostPackage())) {
      return true;
    }

    TypeDecl C = field.hostType(); // C is the type in which the field is declared.
    if (field.isProtected()) {
      for (TypeDecl S = this; S != null; S = S.isNestedType() ? S.enclosingType() : null) {
        if (S.instanceOf(C)) {
          if (!field.isInstanceVariable() || qualifier.instanceOf(S)) {
            return true;
          }
        }
      }
    }
    return false;
  }
```

My version just changes the for-loop to also check that `Q <: S`.

With this version, it became apparent that name resolution also has to be updated because the qualifier is not yet typed, as you pointed out.

After also updating name resolution, it seems to work for all tests. I'll do some more testing and add tests for more cases before committing the fix.

### Jesper Öqvist - 2018-02-01

Now I know why the super access condition was there in the current implementation. A super access is neither an *ExpressionName* nor is it a *Primary* expression, so neither of the two clauses in 6.6.2.1 apply when the qualifier expression is a super access.

A test for super access:

`C.java`:

```
package p1;
public class C {
  protected int f;

  protected void m() {
  }
}
```

`Test.java`:

```
// Test accessing a protected member of a superclass in another package using a super access.
// .result: COMPILE_PASS
import p1.C;

class Test extends C {
  int pass() {
    Test.super.m();
    return super.f;
  }
}
```

### Jesper Öqvist - 2018-02-01

@ohamel-ss If you don't mind me asking, what are you using ExtendJ for? In a previous thread you mentioned that you used it as a frontend to Soot. Are you working on abc, or some other project?

### Jesper Öqvist - 2018-02-01

Improve protected member access control

This fixes errors in the implementation of `Expr.mayAccess(Variable)`,
and changes it into a synthesized attribute (was an inter-type method declaration).

Also removed the helper method `TypeDecl.mayAccess(Expr, Variable)`.

fixes #299 (bitbucket)


→ <<cset e012535003d9>>

### Olivier Hamel - 2018-02-04

@joqvist Sorry for delay; things are hectic. I'm currently working on a project that needs precise source-span to a simplified IL (in this case Jimple), ideally an exact line/column match. I also need something that can process the majority of Java 8, because we actually need to run this on Live(TM) code from real projects. All this is fed into an experimental program analysis tool (whimsicality named 'ducktective').

Soot doesn't have any frontends which supported Java 8 & precise src-mapping. I used abc as a base/guideline, and overhauled quite a bit of it to support precise IL tagging and to work with modern extendj. (There have been quite a few breaking changes since this project was known as jastaddj.) Adding back-end support for new language features was relatively easy though.

### Jesper Öqvist - 2018-02-05

@ohamel-ss Interesting, I hope it works well for your application. If you publish anything about this I'd be interested to read it!

ExtendJ is much more stable right now than it has been since it was called JastAddJ. There were several important refactorings done but now I don't anticipate any large future change except for improved Java 8 type inference and eventual Java 9 support.
