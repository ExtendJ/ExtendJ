# Source file processing order not determined by command-line

**Status:** resolved

Source files are not processed in the order they are listed on the command-line. The simple fix is to change the `files` set in `Options` to be a `LinkedHashSet` so that the iteration order is same as the initial insertion order.

The source file processing order is important when regression testing error messages.

## Comments

### Jesper Öqvist - 2014-02-26

Process source files in  command-line order

fixes issue #57 (bitbucket)


→ <<cset d17f7989a298>>
