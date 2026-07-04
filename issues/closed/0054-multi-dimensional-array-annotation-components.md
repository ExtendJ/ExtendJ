# Multi-dimensional array annotation components

**Status:** resolved

JastAddJ allows multidimensional arrays as annotation components. This should not be allowed. Example from [JLSv7, Chapter 9, $9.6.1](http://docs.oracle.com/javase/specs/jls/se7/html/jls-9.html#jls-9.6.1):

    @interface Verboten {
        String[][] value();
    }

The above is not a legal annotation declaration, nested array types are not allowed as an annotation element type.

## Comments

### Jesper Öqvist - 2014-02-24

Added tests `annotation/array_02f`, `annotation/array_03f`.

### Jesper Öqvist - 2014-02-24

Fixed annotation element type analysis error

fixes issue #54 (bitbucket)


→ <<cset 97870a6cef60>>
