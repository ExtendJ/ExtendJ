# All types are subtypes of UnknownType

**Status:** resolved

*ExtendJ 8.0.1-167-g94430ce Java SE 8*

The subtype equation for UnknownType defines all types as subtypes of UnknownType.

This was originally done to avoid type additional errors resulting from some other root error, however it can mask errors in the type analysis and make some expressions that did not type-check correctly appear to be well-typed.

The relevant equation is around line 500 in java5/frontend/GenericsSubtype.jrag:

```
  eq UnknownType.subtype(TypeDecl type) = true;
```

## Comments

### Jesper Öqvist - 2017-04-21

UnknownType is no longer supertype to everything

fixes #200 (bitbucket)


→ <<cset ef136962657d>>
