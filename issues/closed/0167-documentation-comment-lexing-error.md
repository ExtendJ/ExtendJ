# Documentation comment lexing error

**Status:** resolved

**ExtendJ 8.0.1-109-g33043fe**

There is an error in the scanner regex for documentation comments, causing them to match more than they should. The following test case illustrates the error:

```
public class Test {
  /***/
  public static void main(String[] args) {
    new Test();
  }
  /***/

  public Test() {
  }
}
```

The whole `main` method becomes part of the first documentation comment. This is caused by using the until matcher in the comment regex: `"/**" [^/]* "~*"`
The fix is to replace that until matcher with a regex that matches the comment body but without matching the end of a comment.

As a side note the regular expressions in the JFlex manual are incorrect, and they cause the regression test for issue #144 to fail:

    Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

    TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
    // Comment can be the last line of the file, without line terminator.
    EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
    DocumentationComment = "/**" {CommentContent} "*"+ "/"
    CommentContent       = ( [^*] | \*+ [^/*] )*

I believe the following regex for documentation comment bodies is more correct: `"/**" ([^*/] | "*"+ [^*/]) ([^*] | "*"+ [^*/])* "*"+ "/"` This regex passes all regression tests, plus the new test for this issue.

## Comments

### Jesper Öqvist - 2016-03-23

Fix too greedy documentation comment scanning

Fixed an error in the regular expression for documentation comments, causing
them to span too much text when two empty (/***/) documentation comments were
used in the same file.

fixes #167 (bitbucket)


→ <<cset cc77c86c713e>>
