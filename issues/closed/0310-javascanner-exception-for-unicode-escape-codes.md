# JavaScanner Exception for Unicode Escape Codes

**Status:** resolved

I am using `java8/extendj.jar` built from `d4d25af7` (giving `ExtendJ 8.1.2-15-gd4d25af Java SE 8`).

When tokenizing code that contains [Unicode escapes](https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.3) using `org.extendj.scanner.JavaScanner` the `nextToken()` method throws an exception with the message `illegal escape sequence "\u"`.

I had a brief look at the grammar definition and I don't see Unicode escapes in there.

Find attached an example project containing a test class that triggers the exception. Just execute `./gradlew test`.

As in the passing test cases I would expect JavaScanner to produce a token whose value is a String containing the Unicode escape sequence.

## Attachments

- [extendj-unicode-bug.zip](../attachments/310/extendj-unicode-bug.zip) (uploaded by Georg Seibt)

## Comments

### Jesper Öqvist - 2019-04-05

There is a separate class `org.extendj.scanner.Unicode` which takes care of filtering unicode escapes. It inherits from `FilteredReader`, so you can use it like this:

```java
    @Test
    public void escapedUnicodeChar() throws Exception {
        JavaScanner scanner = new JavaScanner(new Unicode(new StringReader("\"\uD83D\uDE2F\"")));
        tokenize(scanner);
    }
```

However, it does not seem to work perfectly with your example code because apparently JFlex (`JavaScanner`) expects all `read()` calls to read non-zero numbers of characters but `Unicode` can read zero characters in some cases:

```
UnicodeTest > escapedUnicodeChar FAILED
    java.io.IOException: Reader returned 0 characters. See JFlex examples for workaround.
        at org.extendj.scanner.OriginalScanner.zzRefill(OriginalScanner.java:898)
        at org.extendj.scanner.OriginalScanner.nextToken(OriginalScanner.java:1161)
        at org.extendj.scanner.JavaScanner.nextToken(JavaScanner.java:361)
        at UnicodeTest.tokenize(UnicodeTest.java:42)
        at UnicodeTest.escapedUnicodeChar(UnicodeTest.java:30)
```

### Georg Seibt - 2019-04-05

Ah I see, my bad for not spotting that class. I suppose you should always wrap the Reader given to JavaScanner in that then?

Unfortunate that this does not fix the issue. IOException is a lot worse actually, currently we work around the problem by catching the JFlex exception but an IOException is not something we want to catch really. That should indicate some unfixable problem (e.g. disk access failed).

Do you think this is an issue you can resolve?

### Jesper Öqvist - 2019-04-05

Yes, I will change the `Unicode` class so that it blocks on `read()` instead of returning zero!

If you need a quick workaround the JFlex FAQ mentions a workaround using another reader class to wrap a base reader and block on `read`: https://jflex.de/faq.html

### Jesper Öqvist - 2019-04-05

Using `ZeroReader` to wrap `Unicode`, the following works without exception:

```java
    @Test
    public void escapedEscapedUnicodeChar() throws Exception {
        JavaScanner scanner = new JavaScanner(new ZeroReader(new Unicode(new StringReader("\"\\uD83D\\uDE2F\""))));
        tokenize(scanner);
    }
```

See https://github.com/jflex-de/jflex/tree/master/jflex/examples/zero-reader

### Georg Seibt - 2019-04-05

Alright, nice :) Thank you for looking into this so quickly!

### Jesper Öqvist - 2019-04-06

I have updated the `Unicode` class in ExtendJ and renamed it to `UnicodeEscapeReader`. I deprecated the old `Unicode` class, but it will just delegate to the new `UnicodeEscapeReader` for the time being.

I also added tests that mirror your test to ensure that we don't get the above mentioned JFlex scanner errors due to `read()` reading zero-length character sequences.

### Jesper Öqvist - 2019-04-06

Improve Unicode escape handling

Renamed class Unicode in package org.extendj.scanner to UnicodeEscapeReader,
to better describe what the class does. There is now a deprecated class in
place of the old Unicode class which just delegates to UnicodeEscapeReader.

UnicodeEscapeReader improves upon the old Unicode class in a few ways:

* read(char[], int, int) will read zero characters in fewer cases,
  working better with our JFlex scanners (which throw an error if zero
  characters are read).
* Simplified the implementation to only do Unicode escape translation
  in one place.

fixes #310


→ <<cset cff8b8990e80>>
