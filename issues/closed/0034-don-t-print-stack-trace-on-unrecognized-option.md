# Don't print stack trace on unrecognized option

**Status:** resolved

Invoking JastAddJ with the option `--version` produces the following output:

    Exception in thread "main" java.lang.Error: Command line argument error: option --version is not defined
            at AST.Options.addOptions(Options.java:111)
            at AST.Frontend.processArgs(Frontend.java:177)
            at AST.Frontend.process(Frontend.java:46)
            at org.jastadd.jastaddj.JavaCompiler.compile(JavaCompiler.java:23)
            at org.jastadd.jastaddj.JavaCompiler.main(JavaCompiler.java:18)

We should not print stack traces for such simple errors.

## Comments

### Jesper Öqvist - 2022-06-30

Fixed by commit 5147a7177f1b8a0dba8dbc4ca97032038390112f
