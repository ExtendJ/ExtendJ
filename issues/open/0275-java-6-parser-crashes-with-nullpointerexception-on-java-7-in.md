# Java 6 parser crashes with NullPointerException on Java 7 input

*ExtendJ 8.1.0-26-g4f6c910 Java SE 6*

The Java 6 parser crashes when trying to parse the file `src/main/org/apache/tools/ant/taskdefs/Tar.java` from Apache Ant 1.10.1. The parser should print an error message instead.

This is the stack trace printed by ExtendJ:

```
Fatal exception:
java.lang.Error: ./src/main/org/apache/tools/ant/taskdefs/Tar.java: null
        at org.extendj.ast.ClassSource.parseCompilationUnit(ClassSource.java:157)
        at org.extendj.ast.PathPart.getCompilationUnit(PathPart.java:133)
        at org.extendj.ast.Program.addSourceFile(Program.java:106)
        at org.extendj.ast.Frontend.run(Frontend.java:137)
        at org.extendj.JavaDumpTree.run(JavaDumpTree.java:79)
        at org.extendj.JavaDumpTree.main(JavaDumpTree.java:50)
Caused by: java.lang.NullPointerException
        at beaver.Parser$TokenStream.remove(Parser.java:242)
        at beaver.Parser.recoverFromError(Parser.java:669)
        at org.extendj.parser.JavaParser.recoverFromError(JavaParser.java:846)
        at beaver.Parser.parse(Parser.java:470)
        at beaver.Parser.parse(Parser.java:411)
        at org.extendj.parser.JavaParser.parse(JavaParser.java:825)
        at org.extendj.ast.Program$2.parse(Program.java:87)
        at org.extendj.ast.ClassSource.parseCompilationUnit(ClassSource.java:143)
```
