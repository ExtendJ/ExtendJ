# Bad super constructor error message

**Status:** resolved

If a super constructor invocation does not match some existing super constructor the error message JastAddJ gives is bad:

```
Test.java:3:
  Semantic Error: no constructor named AST.SuperConstructorAccess
```

## Comments

### Jesper Öqvist - 2014-12-08

Improved constructor lookup error message

fixes issue #82 (bitbucket)


→ <<cset 896543e61356>>
