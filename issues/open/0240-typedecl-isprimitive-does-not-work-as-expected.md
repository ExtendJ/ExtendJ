# TypeDecl.isPrimitive() does not work as expected

*ExtendJ 8.0.1-235-g49efefa Java SE 8*

The `isPrimitive` attribute returns true for class types like `java.lang.Integer` and `java.lang.Boolean`. This behavior seems counter-intuitive.

It would probably be better if `isPrimitive` returns true only for instances of `PrimitiveType`.

Currently, `isPrimitive` is not an inverse of the `isReferenceType` attribute.

Changing `isPrimitive` may have undesired, so it may not be safe to change it.

## Comments

### Jesper Öqvist - 2017-12-15

Fix error in BooleanType.emitCastTo(_, _)

The condition for generating a boxing operation for a boolean operand was
incorrect due to the behaviour of the isPrimitive attribute which returns true
for both the boxed and primitive boolean type.

fixes #240 (bitbucket)


→ <<cset 180fdc7074cd>>

### Jesper Öqvist - 2017-12-15

This was incorrectly closed.
