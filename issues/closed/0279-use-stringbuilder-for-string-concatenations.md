# Use StringBuilder for String concatenations

**Status:** resolved

*ExtendJ 8.1.0-27-gcd7effa Java SE 5*

ExtendJ implements string concatenation by using an implicit `StringBuffer` object. The `StringBuilder` class would be preferable since it behaves identically to `StringBuffer` except that its methods are not `synchronized`. There is no need to use thread-safe string buffers since each buffer object is only visible to a single thread.

Javac implements string concatenation by using `StringBuilder`.

## Comments

### Jesper Öqvist - 2018-01-07

String concatenation with StringBuilder

This changes code generation for string concatenation expressions (+, +=) to
use StringBuilder instead of StringBuffer.

fixes #279 (bitbucket)


→ <<cset e6bd04c89dfe>>
