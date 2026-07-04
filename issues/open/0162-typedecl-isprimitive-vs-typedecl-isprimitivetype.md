# TypeDecl.isPrimitive() vs TypeDecl.isPrimitiveType()

Currently there exists two separate attributes named `isPrimitive` and `isPrimitiveType`, which behave differently.

The `isPrimitive` version returns true if the callee is a primitive type, or can be unboxed to a primitive type. The `isPrimitiveType` version does not test for unboxing.

The attributes are too similarly named, and it is easy to imagine using the wrong attribute and assuming it would work differently. I think the unboxing check is unintuitive and needs to be clearly indicated in the attribute name.

Another potential problem is that the `isPrimitiveType` attribute returns true for `UnknownType`, which I think seems counterintuitive.
