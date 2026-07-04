# Investigate ditching branch collections

**Status:** resolved

It may be possible to completely remove branch collections. At least the targetStmt attribute should not require computing the full branch set for each containing branch propagation node.

## Comments

### Jesper Öqvist - 2013-08-11

The branchTarget method could be rewritten as an inherited attribute. In practice it already works as an inherited attribute.

### Jesper Öqvist - 2013-08-12

After some investigation I have come to the conclusion that the branch collection is required for definite assignedness analysis. It does not seem practical to try and implement it in a more lazy way. The simplest thing to do is to find all branches that can reach the statement by recursion (keep in mind that branches inside try-statements can be blocked by the finally-block).
