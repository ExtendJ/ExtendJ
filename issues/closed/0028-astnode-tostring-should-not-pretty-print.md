# ASTNode.toString should NOT pretty-print

**Status:** resolved

`ASTNode.toString` should NOT pretty-print the node.

## Comments

### Jesper Öqvist - 2013-11-20

Added ASTNode.prettyPrint() for pretty printing

* added the method ASTNode.prettyPrint() in PrettyPrint.jadd
* cleaned up some code
* replaced several uses of StringBuffer with StringBuilder.

see issue #28 (bitbucket)


→ <<cset 8dbee0db8384>>

### Jesper Öqvist - 2013-11-20

ASTNode.toString no longer pretty prints

fixes issue #28 (bitbucket)


→ <<cset 39f7c6a1ff8a>>
