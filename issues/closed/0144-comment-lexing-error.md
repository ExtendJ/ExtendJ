# Comment lexing error

**Status:** resolved

**ExtendJ 8.0.1-g25ae2d5**

There is an error in the scanning of multiline comments causing some empty comments to extend too far. For example:

```java
public class Test {
  /**/
  public static void main(String[] args) {
    new Test();
  }

  /**/
  public Test() {
  }
}
```

The main method is removed in the above class.

## Comments

### Jesper Öqvist - 2016-03-01

The error seems to be caused by the documentation comment regex in ExtendJ:

```DocumentationComment = "/**" ~"*/"```

This lexes the whole `/**/.../**/` part as a single documentation comment.

### Jesper Öqvist - 2016-03-01

The fix is to simply change the regex to the following:

```DocumentationComment = "/**" [^/] ~"*/"```

### Jesper Öqvist - 2016-03-01

Fix documentation comment regex

Fixed an error in the documentation comment regex which caused some comments to
eat up too many input tokens.

fixes #144 (bitbucket)


→ <<cset 4a602e2190bd>>
