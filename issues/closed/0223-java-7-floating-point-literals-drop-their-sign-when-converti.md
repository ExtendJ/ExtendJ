# Java 7 - Floating point literals drop their sign when converting to constant

**Status:** resolved

J4 & J7 modes have different implementations for `<F32/F64>.constant()`.
J4's impl' appears to consider the sign when emitting `Constant`s. (Not tested, just reading the code.)
J7's impl' feeds `normalizeDigits`, which drops the sign, to `parseFloat`.

Demonstrator:
```java
float f = Double.NEGATIVE_INFINITY;
```
Const-expr-ness will propagate from `Double.NEGATIVE_INFINITY`, which will lead to an attempt to parse `"Infinity"`.  This constant occurs b/c `Double` was loaded from a class file; the literal's token was synthesised from the bytecode by `toString`ing the F64 value.

Suggested hot-fix for this & the other issue I'm about to open regarding POSITIVE_INFINITY:
```java
  eq FloatingPointLiteral.constant() {
    try {
      float value;
      if (getLITERAL().equals(Float.POSITIVE_INFINITY + ""))
        value = Float.POSITIVE_INFINITY;
      else if (getLITERAL().equals(Float.NEGATIVE_INFINITY + ""))
        value = Float.NEGATIVE_INFINITY;
      else if (getLITERAL().equals(Float.NaN + ""))
        value = Float.NaN;
      else
        value = Float.parseFloat((isNegative() ? "-" : "") + normalizedDigits());

      return Constant.create(value);
    } catch (NumberFormatException e) {
      Constant c = Constant.create(0.0f);
      c.error = true;
      return c;
    }
  }
```

Same general idea for `DoubleLiteral`.

## Comments

### Olivier Hamel - 2017-12-07

Opened second issue for the related constant-from-class file issue: #224

### Olivier Hamel - 2017-12-07

NOTE: You won't generally see this when parsing java source code because there'll be a `MinusExpr` as the parent to the literal. You'll mostly get kicked by this when loading constant fields from class files.

### Jesper Öqvist - 2017-12-08

It seems better to just store the bytecode constant value in the literal class and recall it when building a Constant object, instead of having to parse the pretty-printed version of the literal.

### Jesper Öqvist - 2017-12-08

Added test for this issue: `jsr334/codegen/literal_01p`

### Jesper Öqvist - 2017-12-08

Thank you for reporting this issue! Numeric bytecode constants will no longer be round-tripped through strings.

### Jesper Öqvist - 2017-12-08

Avoid redundant constant number string round-trip

This avoids converting bytecode constant numbers to string and then back.
This fixes an error where some input bytecode constants were not correctly
propagated to bytecode output.

fixes #223 (bitbucket)


→ <<cset 53f9cf3e95ad>>

### Olivier Hamel - 2017-12-08

Minor correction of hot-fix. Generalised to handle NaNs. Does not preserve the exact NaN value.
