# SynchronizedStmt.prettyPrint() incorrect

**Status:** resolved

Commit a780b13 removes the parentheses around the expression in synchronized statements. Probably, because there are two occurrences of "SynchronizedStmt" in Java4PrettyPrint.tt - the [first one](https://bitbucket.org/extendj/extendj/src/cc1f6e3c3f72ef104215119b2cab3f74efdaeb12/pretty-print/Java4PrettyPrint.tt?at=master&fileviewer=file-view-default#Java4PrettyPrint.tt-109) with parentheses and the [second one](https://bitbucket.org/extendj/extendj/src/cc1f6e3c3f72ef104215119b2cab3f74efdaeb12/pretty-print/Java4PrettyPrint.tt?at=master&fileviewer=file-view-default#Java4PrettyPrint.tt-193) without.

## Comments

### Jesper Öqvist - 2015-11-30

Thank you for this bug report! Indeed, the error was caused by the duplicate `SynchronizedStmt` in Java4PrettyPrint.

### Jesper Öqvist - 2015-11-30

Fix pretty printing of synchronized statements

Fixed missing parenthesis around the expression in pretty printed synchronized
statements.

fixes #126 (bitbucket)


→ <<cset ad78bf5bd1ec>>
