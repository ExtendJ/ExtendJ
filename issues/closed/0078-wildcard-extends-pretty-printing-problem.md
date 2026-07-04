# Wildcard extends pretty printing problem

**Status:** resolved

Example:

    class Wild<T extends java.lang.Comparable<? extends T>> {
    }

pretty prints as

    class Wild<T extends java.lang.Comparable<wildcards.? extends test.Wild@T>> {
    }

## Comments

### Jesper Öqvist - 2014-05-05

The problem is caused by the prettyPrinting method using the fullName attribute to print the type bound of T (extends java.lang...).

### Jesper Öqvist - 2014-05-05

Fixed pretty printing of type bounds

fixes issue #78 (bitbucket)


→ <<cset c0017e0ff18e>>
