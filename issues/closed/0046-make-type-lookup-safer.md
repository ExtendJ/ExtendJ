# Make type lookup safer

**Status:** resolved

The lookupType attribute can return null. The attribute should be made safer by instead returning UnknownType.

## Comments

### Jesper Öqvist - 2015-03-09

In most cases the result of `lookupType` is checked for nullness, however some standard type lookups are not null-checked, for example the ones below:

    syn lazy TypeDecl Program.typeObject() = lookupType("java.lang", "Object");
    syn lazy TypeDecl Program.typeCloneable() = lookupType("java.lang", "Cloneable");
    syn lazy TypeDecl Program.typeSerializable() = lookupType("java.io", "Serializable");

Normally these types will be available, but if they for some reason are not (misconfigured boot classpath) then we get a NullPointerException.

### Jesper Öqvist - 2015-03-09

Made lookupType non-null

Program.lookupType(String,String) now returns the UnknownType instance for
failed type lookups.

fixes #46 (bitbucket)


→ <<cset 416320d9c17c>>
