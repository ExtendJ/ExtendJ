# Enum types should not have ACC_STATIC flag

**Status:** resolved

*ExtendJ 8.1.0-52-gddbdc64 Java SE 8*

Enum types should not have the `ACC_STATIC` flag (0x8) in the generated classfile, even if the enum type is nested.

ExtendJ currently generates the `ACC_STATIC` flag for nested enum types.

## Comments

### Jesper Öqvist - 2018-01-11

Remove ACC_STATIC flag for enum types

fixes #290 (bitbucket)


→ <<cset 8b47e6500175>>
