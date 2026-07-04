# Assign sourceType evaluates to unknown when source type not primitive

**Status:** resolved

A really strange design decision in `AssignExpr`:

    syn TypeDecl AssignExpr.sourceType() = getSource().type().isPrimitive() ? getSource().type() : unknownType();

I noticed this while extending the type system, and got an unexpected compile error for an unhandled case in an addition expression:

    test/T3.java:18:
      Semantic Error: can not assign accounts of type {any:java.util.Set}T3.Account a value of type Unknown

This is a really bad error message because it makes it seem as if the source type is unkown even though the source type in this case is actually known by JastAddJ. The error message is generated this way because it uses the `sourceType` attribute to get the source type name.

Actually removing the `sourceType` attribute seems to have no ill effects. Removing it is probably preferable to changing the semantics in case it is used in an extension.

## Comments

### Jesper Öqvist - 2014-02-19

Removed the sourceType attribute

fixes issue #48 (bitbucket)


→ <<cset 75879601d135>>
