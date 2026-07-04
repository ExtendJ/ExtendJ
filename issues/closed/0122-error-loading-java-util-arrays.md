# Error loading java.util.Arrays.

**Status:** resolved

I'm running current master (`999541`), but I experienced this issue also with significantly older versions. When traversing the AST of the attached file, e.g., using doFullTraversal() or by using the pretty-printer, I get this error:

`Exception in thread "main" java.lang.Error: Error loading /usr/lib/jvm/jdk-8-oracle-x64/jre/lib/rt.jar:java/util/Arrays.class`

Also, the JVM is shut down. I'm running oracle jdk version 1.8.0_51 on debian unstable. Compiling the file with javac works fine.

## Attachments

- [ArraysTest.java](../attachments/122/ArraysTest.java) (uploaded by Olaf Lessenich)
- [stacktrace](../attachments/122/stacktrace) (uploaded by Olaf Lessenich)

## Comments

### Jesper Öqvist - 2015-07-30

This is caused by trying to parse the Java 8  class library using the Java 7 bytecode parser. There is currently no checking of MAJOR/MINOR version of classfiles in the ExtendJ bytecode parser, so it crashes during parsing. This should be fixed so you at least get a readable error message.

### Jesper Öqvist - 2015-07-31

Improve error messages for wrong classfile version

fixes #122 (bitbucket)


→ <<cset e367d8eee76a>>

### Olaf Lessenich - 2015-08-03

Thanks very much for the quick help! Works perfectly!
