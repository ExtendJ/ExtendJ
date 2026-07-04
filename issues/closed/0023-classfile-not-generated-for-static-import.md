# Classfile not generated for static import

**Status:** resolved

In jjtest the test `runtime/jsr334/diamond/diamond01` fails because JastAddJ does not generate a classfile for the `runtime.Test` class, from which a static method is used.

## Comments

### Jesper Öqvist - 2013-08-27

This seems to fail even if the "wildcard" import is changed to a single static import.

### Jesper Öqvist - 2013-08-27

Added the test `tests/codegen/static-import01` to test this bug.

### Jesper Öqvist - 2013-08-29

Generate code for imported from-source types

fixes issue #23 (bitbucket)
fixes issue #24 (bitbucket)


→ <<cset 65be7b95b7de>>
