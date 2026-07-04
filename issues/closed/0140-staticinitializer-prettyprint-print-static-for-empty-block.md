# StaticInitializer.prettyPrint print static for empty block

**Status:** resolved

Code of StaticInitializer.prettyPrint in java4/frontend/PrettyPrint.jadd print the keyword static even for an empty body. Its code is the following
```
#!java
public void StaticInitializer.prettyPrint(PrettyPrinter out) {
    out.print("static ");
    if (!blockIsEmpty()) {
      out.print(getBlock());
    }
  }
```
it should be replaced by
```
#!java
public void StaticInitializer.prettyPrint(PrettyPrinter out) {
    if (!blockIsEmpty()) {
      out.print("static ");
      out.print(getBlock());
    }
  }
```

## Comments

### Jesper Öqvist - 2016-02-24

Don't pretty-print static initializer if empty

fixes #140 (bitbucket)


→ <<cset eb49d1c220fd>>
