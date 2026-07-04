# Local class should not be accessible from an outer scole

**Status:** resolved

*ExtendJ 8.0.1-161-g2081568 Java SE 8*

A local class declaration is not accessible outside its scope.

[JLS 8 §6.3](https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.3) defines the scope of local class declarations:

> The scope of a local class declaration immediately enclosed by a block (§14.2) is the rest of the immediately enclosing block, including its own class declaration.


ExtendJ currently does not give an error when a local class is used outside its immediately enclosing block:

```java
// .result=COMPILE_FAIL
public class Test {
  public static void main(String[] args) {
    if (args.length == 2) {
      class Inner {
        public int i;

        public String toString() {
          return "i: " + i;
        }
      }
      Inner inner = new Inner();
      inner.i = args.length;
      System.out.println(inner);
    }

    // Error: Outside the scope where Inner was declared.
    Inner outer = new Inner();
  }
}
```

## Comments

### Jesper Öqvist - 2017-04-24

Report errors for uses of unknown qualified types

Improved error checking for type accesses to handle unknown qualified types.

Adjusted the error messages for the inaccessible type error and the ambiguous
type error.

fixes #192 (bitbucket)
fixes #193 (bitbucket)
fixes #194 (bitbucket)
fixes #195 (bitbucket)
fixes #196 (bitbucket)


→ <<cset 53debda90fb3>>
