# Some command-line arguments fail when read from file.

**Status:** resolved

*ExtendJ 8.1.1 Java SE 8*

The command-line argument `-XDuseUnsharedTable=true` is not accepted when read from an argument file (providing the arg `@<filename>.txt`), but it is when given from the command line.

Expected result: probably ignoring the argument, since it is a performance-related argument that is probably not that relevant for extendj.

Actual result:

Exception in thread "main" java.lang.Error: Command line argument error: option -XDuseUnsharedTable=true is not defined
	at org.extendj.ast.Options.addOptions(Options.java:137)
	at org.extendj.ast.Frontend.processArgs(Frontend.java:316)
	at org.extendj.JavaCompiler.processArgs(JavaCompiler.java:169)
	at org.extendj.ast.Frontend.run(Frontend.java:133)
	at org.extendj.JavaCompiler.run(JavaCompiler.java:101)
	at org.extendj.ExtensionMain.main(ExtensionMain.java:10)

## Comments

### Jesper Öqvist - 2018-05-02

The `-X` family of options are non-standard options which can change between implementations, so they should be safe to ignore. See https://docs.oracle.com/javase/7/docs/technotes/tools/windows/javac.html


> An additional set of non-standard options are specific to the current virtual machine and compiler implementations and are subject to change in the future. Non-standard options begin with -X.

### Jesper Öqvist - 2018-05-02

I get identical behavior when I call ExtendJ with `-XDuseUnsharedTable=true` on the command line and in an argument file:

```
Σ 14:40:04 ~/git/extendj $ java -jar extendj.jar -version
ExtendJ 8.1.1-30-g64560eb3 Java SE 8
Σ 14:40:33 ~/git/extendj $ java -jar extendj.jar -XDuseUnsharedTable=true
Exception in thread "main" java.lang.Error: Command line argument error: option -XDuseUnsharedTable=true is not defined
        at org.extendj.ast.Options.addOptions(Options.java:138)
        at org.extendj.ast.Frontend.processArgs(Frontend.java:416)
        at org.extendj.JavaCompiler.processArgs(JavaCompiler.java:169)
        at org.extendj.ast.Frontend.run(Frontend.java:147)
        at org.extendj.JavaCompiler.run(JavaCompiler.java:101)
        at org.extendj.JavaCompiler.main(JavaCompiler.java:61)
        at org.jastadd.extendj.JavaCompiler.main(JavaCompiler.java:39)
Σ 14:40:41 ~/git/extendj $ echo "!$" > args && java -jar extendj.jar @args
echo "-XDuseUnsharedTable=true" > args && java -jar extendj.jar @args
Exception in thread "main" java.lang.Error: Command line argument error: option -XDuseUnsharedTable=true is not defined
        at org.extendj.ast.Options.addOptions(Options.java:138)
        at org.extendj.ast.Frontend.processArgs(Frontend.java:416)
        at org.extendj.JavaCompiler.processArgs(JavaCompiler.java:169)
        at org.extendj.ast.Frontend.run(Frontend.java:147)
        at org.extendj.JavaCompiler.run(JavaCompiler.java:101)
        at org.extendj.JavaCompiler.main(JavaCompiler.java:61)
        at org.jastadd.extendj.JavaCompiler.main(JavaCompiler.java:39)
```

### Jesper Öqvist - 2018-05-02

Ignore non-standard options

Non-standard javac options are now ignored when passed on the command line or
via an argument file.

fixes #305 (bitbucket)


→ <<cset 20eb904b2624>>
