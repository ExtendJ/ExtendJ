# Get rid of BodyDeclList

**Status:** resolved

`BodyDeclList` is a node type used to build substituted member declarations in generic types. The problem with `BodyDeclList` is that inherited attributes don't work as expected since `BodyDeclList` is a `List` subtype. Inherited equations cause problems because they may not be able to compute a real child index for the body decl list children:

```java
eq ClassDecl.getBodyDecl(int i).x() { ... } // ERROR: index i can't be computed for NTA children of BodyDeclList
```

As the above example illustrates, when the `x()` attribute is computed for an NTA child of BodyDeclList, then the child index becomes incorrectly computed in the generated code for the inherited equation.

An example from generated code in ExtendJ:

```java
  public SimpleSet<TypeDecl> Define_lookupType(ASTNode _callerNode, ASTNode _childNode, String name) {
    if (_callerNode == getBodyDeclListNoTransform()) {
      // @declaredat /Users/jesper/git/extendj/java5/frontend/Generics.jrag:764
      int index = _callerNode.getIndexOfChild(_childNode);
      {
          SimpleSet<TypeDecl> result = memberTypes(name);
          if (getBodyDecl(index).visibleTypeParameters()) { // ERROR: Null pointer exception when _childNode is an NTA child of BodyDeclList.
            result = addTypeVariables(result, name);
```

## Comments

### Jesper Öqvist - 2016-03-18

This test case currently fails because of a `NullPointerException` caused by using `BodyDeclList`:

```java
// Test parameter type substitution and type lookup.
// .result=COMPILE_PASS
class Combined<U, V> {
  public Combined(U u, V v) {
  }
}

class Test<A> {
  Test<A> val;
  class Op<B> {
    Combined<A, B> op(Test<B> x) {
      return (val.new Op<B>()).combine(x.val);
    }
    Combined<A, B> combine(Test<B> x) {
      return new Combined<A, B>(val, x.val);
    }
  }
}
```

The `NullPointerException` occurs on the line commented above in `Define_lookupType`.

### Jesper Öqvist - 2016-03-19

Replace List subtype BodyDeclList

BodyDeclList was used for on-demand creation of substituted body declarations
in parameterized types. This has been replaced by using rewrites instead.
This fixes a NullPointerException caused by evaluating inherited attributes on
the NTA children of a BodyDeclList node.

fixes #166 (bitbucket)


→ <<cset 4f7e08dee6cb>>

### Jesper Öqvist - 2016-03-22

Reverted the refactoring commit that fixed this issue because it broke some test cases. Working on fixing the issues now.

### Jesper Öqvist - 2016-03-22

Replace List subtype BodyDeclList

BodyDeclList was used for on-demand creation of substituted body declarations
in parameterized types. This has been replaced by using rewrites instead.
This fixes a NullPointerException caused by evaluating inherited attributes on
the NTA children of a BodyDeclList node.

fixes #166 (bitbucket)


→ <<cset b4b9a85c4873>>
