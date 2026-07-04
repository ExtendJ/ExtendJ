# Circularity in type lookup

**Status:** resolved

**ExtendJ 7.1.1-323-g46f8b96 Java SE 8**

There is a circular dependency in the type lookup algorithm:

```
package pkg.test;

import static pkg.test.A3.VAL;

public final class A3 implements Runnable {
        public static final int VAL = 8;

        Character.Subset f;

        @Override
        public void run() { }
}
```

When `Runnable` is looked up the import statement is checked for imported types which causes the `memberTypes` attribute to be evaluated on `pkg.test.A3`. This in turn leads to a recursive lookup of `Runnable`. Removing the field `f` makes the same recursive behavior not happen, not sure why yet.

## Comments

### Jesper Öqvist - 2015-12-04

Fix circular definition of importedTypes attribute

The attribute StaticImportDecl.importedTypes was effectively circular. This
fixes the attribute declaration and removes caching for attributes that
participate in the same circular evaluation.

fixes #117 (bitbucket)


→ <<cset 930e00da18d4>>
