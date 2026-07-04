# isEffectivelyFinal has a confusing name, and should probably deviate from the JLS

**Status:** resolved

The Java Language Specification defines "effectively final" as different from "declared final". The problem is that the term "effectively final" has the connotation that it should include "declared final". We perhaps should change the semantics of `isEffectivelyFinal` to match the intuitive understanding of what the attribute name implies.

## Comments

### Jesper Öqvist - 2015-07-22

isEffectivelyFinal is no longer excludes isFinal

fixes #121 (bitbucket)


→ <<cset cb308b834138>>
