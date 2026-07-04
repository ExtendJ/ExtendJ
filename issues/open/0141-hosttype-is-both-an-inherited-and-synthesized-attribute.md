# hostType() is both an inherited and synthesized attribute

**ExtendJ 8.0.1-54-g387b459**

The `hostType()` attribute is both declared as an inherited attribute and as a synthesized attribute. The two versions of the attribute behave differently. For example, if `hostType()` is evaluated on a `TypeDecl` then the attribute returns the `TypeDecl` itself, however if it is evaluated on an `Expr`, then the attribute returns the closest enclosing type. It might be assumed that `hostType()` always returns the enclosing type, so a nested type would return its enclosing host type, not itself.

The synthesized version of this attribute should probably be removed to not cause confusion about what `hostType()` evaluates to.
