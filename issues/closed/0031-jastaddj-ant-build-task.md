# JastAddJ Ant Build Task

**Status:** wontfix

Creating extensions with JastAddJ is cumbersome due to the complicated build process. The builds include/exclude files in a particular order that must be maintained even in extensions. This problem could be alleviated by having a build tool dedicated for JastAddJ building where you only specify an extension directory and the tool takes care of the rest.

The build tool could be implemented as an Ant Task. Using the task should look something like this:

    <jjgen outdir="src/gen">
        <module dir="java4"/>
        <module dir="java5"/>
        <module dir="java6"/>
        <module dir="java7"/>
    </jjgen>

The build tool would generate scanner, parser and compiler by invoking the scanner generator, parser generator and JastAdd2. Before invoking the scanner and parser generators the tool should first combine the scanner and parser specifications in each module directory in the correct order. Figuring out the correct order can be done based on file name and module order.

The build tool should only generate source files, which are then compiled using the standard javac task.

## Comments

### Jesper Öqvist - 2016-02-24

The [JastAddGradle](https://bitbucket.org/joqvist/jastaddgradle) plugin has made this issue obsolete. JastAddGradle allows simpler ExtendJ extension development.
