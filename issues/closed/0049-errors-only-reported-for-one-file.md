# Errors only reported for one file

**Status:** resolved

It seems like there is a problem with the error reporting in JastAddJ causing it to only report errors for one input file even if there are errors in many input files.

## Comments

### Jesper Öqvist - 2014-02-23

This problem has some surprising effects. All else being equal, the semantic errors are affected by the filenames of source files because this changes the ordering of compilation units in the root Program node.

This means that you may for identical projects see different errors reported depending on where the project is located on disk.

I ran into this problem in the multiplicity project, where I was seeing one runtime error in code generation for a method access where the declaration had a problem. In some cases when I moved the test files I got the semantic errors for the method declaration because it was processed before doing code generation for the method access.

The two errors were mutually exclusive - if the code generation error happened first it prematurely aborted the compilation, while if the semantic error was found first then no further compilation units were processed.

The only sane way to handle the errors would be to try to catch all semantic errors before code generation.

### Jesper Öqvist - 2014-02-24

Fixed in commit 70c53c885363212f2c794dc549863bc2983083fb
