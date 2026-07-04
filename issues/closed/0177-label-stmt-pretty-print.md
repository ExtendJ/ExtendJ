# Label stmt pretty print

**Status:** resolved

The label statement pretty print prints only its label.

in java4/frontend/PrettyPrint.jadd line 436  ```out.print(getStmt());``` should be added at the end of LabeledStmt.prettyPrint.

## Comments

### Jesper Öqvist - 2016-07-25

Fix error in labeled statement pretty printing

Labeled statements now pretty-print their statement, not only the label.

fixes #177 (bitbucket)


→ <<cset 67a613912ced>>
