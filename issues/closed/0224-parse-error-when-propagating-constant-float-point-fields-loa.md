# Parse error when propagating constant float point fields loaded from class files

**Status:** resolved

(Sequel to issue #223.)

Issue: Exactly what the title says.

Demonstrator:
```java
float f = Double.NEGATIVE_INFINITY;
```
Const-expr-ness will propagate from `Double.NEGATIVE_INFINITY`, which will lead to an attempt to parse `"Infinity"`.  This constant occurs b/c `Double` was loaded from a class file; the literal's token was synthesised from the bytecode by `toString`ing the F64 value.

Same suggested fix as #223. The expanded domain of `<F32/F64>.constant()` shouldn't be externally visible, so long as the lexer/parser rejects 'Infinity' as a float literal.

PS: Suggested fix doesn't handle various flavours of NaN.

PPS: Might be worth checking out if Java's `toString` of floating-point types is guaranteed to round-trip through `parseFloat/Double` for finite values. Disappointingly, this doesn't tend to be true for most formatters...

## Comments

### Jesper Öqvist - 2017-12-08

Can you provide a minimal example that produces the parse error? The demonstrator you gave does not cause a parse error when inserted as a local variable.

### Olivier Hamel - 2017-12-08

This is effectively resolved by the fix for #223, which avoids the round-trip-through-string dance.

Ignoring the minor typo in the sample (`float` instead of `double`), I'm uncertain as to why the demonstrator did not provoke the issue for you. It does in our build, but we're running a slightly modified version of extendj with a few quick hacky hot-fixes that's spliced onto the front of Soot. It could be our jimple backend that's causing the issue to manifest, pardon...

### Jesper Öqvist - 2017-12-08

Okay, I'm closing this then. Thanks again for reporting these issues!
