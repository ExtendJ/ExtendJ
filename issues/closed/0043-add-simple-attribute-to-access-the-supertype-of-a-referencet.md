# Add simple attribute to access the supertype of a ReferenceType

**Status:** resolved

I can not find any attribute which gives me the supertype of a `ReferenceType`. It seems like such an attribute would be useful to have.

## Comments

### Jesper Öqvist - 2022-06-30

The relevant attribute was `TypeDecl.superclass()` however, the name `supertype()` is more intuitive for this attribute and an attribute with that name was added in `java4/backend/VerificationTypes.jrag` a while ago. Since this attribute is useful for frontend code it should be moved to `java4/frontend`.

### Jesper Öqvist - 2022-06-30

Move TypeDecl.supertype() attribute from java4/backend to java4/frontend

fixes #43 (bitbucket)


→ <<cset b5889af2b4ed>>
