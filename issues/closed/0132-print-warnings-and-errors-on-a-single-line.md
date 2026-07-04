# Print warnings and errors on a single line

**Status:** resolved

ExtendJ 8.0.1-17-gad3996f Java SE 8

ExtendJ prints warnings and errors in lists per file, like this:

```
Errors:
org/gjt/sp/jedit/bsh/NameSource.java:63,5:
  Syntactic Error: unexpected token "}"
Warnings:
org/gjt/sp/jedit/bsh/classpath/BshClassPath.java:593:
  WARNING: toURL() in java.io.File has been deprecated
org/gjt/sp/jedit/bsh/classpath/BshClassPath.java:665:
  WARNING: toURL() in java.io.File has been deprecated
org/gjt/sp/jedit/bsh/classpath/BshClassPath.java:757:
  WARNING: toURL() in java.io.File has been deprecated
```

I prefer having errors and warnings on a single line, meaning that the above messages would be printed like this:

```
org/gjt/sp/jedit/bsh/NameSource.java:63,5: error: unexpected token "}"
org/gjt/sp/jedit/bsh/classpath/BshClassPath.java:593: warning: toURL() in java.io.File has been deprecated
org/gjt/sp/jedit/bsh/classpath/BshClassPath.java:665: warning: toURL() in java.io.File has been deprecated
org/gjt/sp/jedit/bsh/classpath/BshClassPath.java:757: warning: toURL() in java.io.File has been deprecated
```

This would make ExtendJ output more similar to Javac output and should improve interoperability with Javac.

Another minor issue is that errors and warnings use different capitalization: errors have mixed lower/upper case while warnings are all upper case. This seems odd, we should just do it like javac does and print `error`/`warning` in lower case.

## Comments

### Jesper Öqvist - 2015-12-14

Print warnings and errors on a single line

fixes #132 (bitbucket)


→ <<cset b9b95c7f01d5>>
