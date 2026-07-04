# Don't generate subroutines (JSR/RET) for finally clauses

**Status:** resolved

Inline finally handlers at the appropriate locations.

This is in adherence to the recent deprecation of JSR/RET since bytecode version 51. Removing subroutines fixes some code generation problems that have been caused by local variables being clobbered by finally clause subroutines.

## Comments

### Jesper Öqvist - 2013-12-12

Fixed! See #19
