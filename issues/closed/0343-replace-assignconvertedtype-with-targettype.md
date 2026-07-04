# Replace assignConvertedType with targetType

**Status:** resolved

The attributes `assignConvertedType` and `targetType` have approximately the same purpose. The concept of an expression target type was first part of the Java 8 specification. Before then, target types were only indirectly described as part of assignment conversion rules. It makes sense to backport the `targetType` attribute to the java5 module and remove `assignConvertedType`.

## Comments

### Jesper Öqvist - 2026-06-21

Replace assignConvertedType with targetType

This commit backports the `targetType` and `assignmentContext`
attributes from the java8 module to the java5 module. The
`assignConvertedType` was initially only for generic method inference in
java5, but this purpose can be accomplished with `targetType` instead
and this simplifies the overall attribute system for java8 forward.

This commit updates the java5 generic method inference
(`inferTypeArguments`) to use a new attribute
`Expr.targetTypeInference` to decide when `targetType` is used for the
inference. In java5, `targetTypeInference` is
synonymous with `assignmentContext` and in java8 it is refined to
`assignmentContext || invocationContext`. This matches the Java
specification better and avoids coding in the rules for when
`targetType` is used for inference inside the `targetType` attribute.

As part of applying this refactoring I discovered that generic
method inference for the enhanced for loop variable was incorrect (see
issue #285). The refactoring incidentally fixed this incorrect
inference because enhanced for loop variables were previously treated as
if they were in assignment context.

fixes #285 and #343


→ <<cset 4a17363c6e80>>
