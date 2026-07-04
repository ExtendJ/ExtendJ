# Refactor CompilationUnit list

**Status:** resolved

The Program node has a compilation unit (CU) list that is filled by side effects in the CU iterator.

Can the CU list be made into an NTA or side-effect free attribute?

## Comments

### Jesper Öqvist - 2016-02-24

The side effects remain, but have been handled better by using a robust list to store the parsed compilation units. There is a separate list for library compilation units.

The current side effects to modify these lists are handled in a way that should make compilation unit parsing order irrelevant.

It is a fundamental problem in a declarative Java compiler that you need to be able to do introspection to see which compilation units have been loaded, and this can only be done by using monads or side effects. The side effect approach is in this case more efficient and I think it is the ideal solution for ExtendJ. We just have to be careful that the side effects are not externally visible and multiple evaluations of a single attribute are okay.
