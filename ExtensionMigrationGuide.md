Migration Guide for JastAddJ Extensions
=======================================

* created by GH (jan 2014)
* updated by JO (jan 2014)

The JastAddJ API up to version 7.1 was mostly much unchanged for several years.
Since version 7.1 though many things have changed in JastAddJ in order to
remove side effects, fix errors, and make the code more understandable. The
next release should be much more stable, but right now JastAddJ is changing
very much.

If you are maintaining a JastAddJ extension we understand that these API
changes can be difficult or frustrating to keep up with, but we see the changes
are seen as a necessary evil.

This guide is meant to help with migrating an extension from an older version
of JastAddJ to the current version.  Migrating to the latest version of
JastAddJ means that your extensions will become more conformant with the Java
specification, due to the bug fixes that have been added to JastAddJ since
version 7.1. You should also see increased performance after updating.

Please keep in mind that we are still changing JastAddJ rapidly at this moment,
so if you want to continue with a more stable version you may want to wait for
the next release.

To migrate your extension, we recommend that you start out with an older
version of JastAddJ that works with your extension (in most cases version 7.1
will work), and apply the adaptations one after the other, verifying after each
step that your extension works as expected. You can easily change your
currently checked out version of JastAddJ with the commands

    cd jastaddj
    git checkout commit-id

This guide should be used together with the ChangeLog. The ChangeLog has less
detailed descriptions of some commits, but some changes may be omitted here
and are better covered in the ChangeLog.

If you encounter problems not listed here, please report them on the [JastAddJ
issue tracker at bitbucket][1].

Jan 14, 2014: Fail if output directory does not exist
-----------------------------------------------------

**JastAddJ commit [7e1d8ef][29]**

The `-d` command-line option is used to set the output directory for generated
class files. Javac does not allow using a directory that does not exist,
however previous versions of JastAddJ would just create the output directory.
After this commit JastAddJ will fail with an error message if a non-existent
output directory is specified.

Jan 7-8, 2014: Fixed/improved semantic error messages
-----------------------------------------------------

**JastAddJ commit [f518c1e][26] (Jan 7, 2014)** (Fixed/improved semantic error
messages)

**JastAddJ commit [c572865][27] (Jan 8, 2014)** (Fixed some more semantic error
messages)

These commits fixed an error where some things were not properly pretty-printed
in semantic error messages. Some error messages were also altered to be more
informative and in some cases a little more brief.

The changed error messages are:

+ imported type conflicting with visible type error
+ method invocation type error (added argument index)
+ qualified this access error (added enclosing type name)

Other error messages should be the same as before the Nov 20 commit below.

### Extensions that are affected:

+ If you have tests that check the contents of the changed compile-time error
  messages, these might fail, and you will need to update the oracles.
+ If you have extensions that generate new compile-time error messages, the
  messages might need to be adapted.

### How to adapt:

Go through all your calls to `error("...")`. If you access ASTNodes in them,
using implicit calls to `toString()`, these need to be adapted to call
`prettyPrint()`. For example, replace

    error("illegal to foo " + getP());
    error("illegal to foo " + getQ().name());
    error("illegal to foo " + this);
    error("illegal to foo " + getR().bar());

by

    error("illegal to foo " + getP().prettyPrint());
    error("illegal to foo " + getQ().name());
    error("illegal to foo " + this.prettyPrint());
    error("illegal to foo " + getR().bar().prettyPrint());

(assuming that `getQ().name()` is a `String`, but `getR().bar()` is an `ASTNode`). 

For real examples, see [this NonNullChecker commit][28], and [the c572865
JastAddJ commit][27].

Nov 20, 2013: New semantics for ASTNode.toString()
--------------------------------------------------

**JastAddJ commit [39f7c6a][2]** (`ASTNode.toString()` no longer pretty prints)

Since this commit, the `ASTNode.toString()` method in JastAddJ is redefined to
no longer prettyprint the subtree, but to instead print the AST class name. The
main reason is that the previous behavior makes debugging difficult, as
inspecting nodes triggers prettyprinting. The prettyPrinting also interfered
with the new JastAdd2 tracing feature.

A new method, `ASTNode.prettyPrint()`, was is introduced for prettyprinting.

Many compile-time error messages in JastAddJ rely on the prettyprinting
behavior for the messages to come out right. This is fixed in the commit
[f518c1e][26].

### Extensions that are affected:

If you have tests that check the contents of compile-time error messages, these
might fail.

### How to adapt:

As a temporary measure, you can locally revert the change by adding an aspect
to your extension that refines ASTNode.toString to again prettyprint.  See
[this NonNullChecker commit][3] for an example.

Alternatively (and better), go to the above step.

Oct 17, 2013: Added utf-8 encoding in javac task
------------------------------------------------

**JastAddJ commit [046b7ee][4]** (Set encoding for javac tasks)

This commit adds utf-8 encoding in the javac task in the build file.

### How to adapt:

The javac file encoding defaults to utf-8 on some systems but this can differ
on others.  JastAddJ aspects use utf-8 characters so it is recommended to
explicitly use the utf-8 encoding to avoid encoding-related build problems.

Sep 16, 2013: Renamed JastAddTask
---------------------------------

**JastAddJ commit [450a430][5]** (Updated JastAdd task to org.jastadd.JastAddTask)

Since this commit, JastAddJ uses a newer version of the ant task in the
jastadd2.jar tool `jastadd.JastAddTask` to `org.jastadd.JastAddTask`.

### If you don't adapt:

You will simply get a warning when building.

### How to adapt:

See the above commit for how to update your build file.

Aug 30, 2013: Split src/java directory
--------------------------------------

**JastAddJ commit [f5da610][6]** (Split src/java directory)

In this commit, JastAddJ splits its `src/java` directory into two separate
directories, `src/backend` and `src/frontend`.

### How to adapt:

Update the build file. You should replace the javac path jastaddj/src/java by
either the frontend dir or both the frontend and backend directories. This
depends on if you are only building a frontend, like JavaChecker, or a full
compiler, like JavaCompiler.

For an example of building a frontend, see [this commit of NonNullChecker][7].
If you are building a complete compiler, see the java7/build.xml file in the
[JastAddJ f5da610 commit][8]. 

Aug 29, 2013: Use the RobustMap option
--------------------------------------

**JastAddJ commit [65be7b9][9]** (Generate code for imported from-source types)

Since this commit, JastAddJ relies on using `RobustMaps` for keeping track of
library compilation units.

### How to adapt:

Update the build file to set the default map type in the jastadd task:

    defaultMap="new org.jastadd.util.RobustMap(new java.util.HashMap())"

See [this JSR308 commit][10] for an example.

Note that for this to work, you need to already have adjusted your build file
to compile to a separate directory, as described below. 

Aug 29, 2013: Compile to separate directory
-------------------------------------------

**JastAddJ commit [a1d3576][11]** (Ant script compiles to ant-bin)

To more easily adapt to later commits, adjust your build file to place compiled
class files in a separate directory. This will allow your extension to compile
java files located in the jastaddj directory, without needing to copy them to
your extension directory first.

### Background for the interested:

Originally (pre Apr 15 2013, commit [2fdb430][12]), the JastAddJ build scripts
placed class-files in the same dirs as the source files. From commit
[a1d3576][13], Aug 29 2013, the class files are placed in a directory called
`ant-bin` (in order to not interfere with class files produced by automatically
compiling IDEs like Eclipse, which typically place the class files in `bin`).

### How to adapt:

In your build file, adjust the `javac` task to include a `destdir` attribute.
See JastAddJ's [java7/build.xml file][14] for an example.

You might now be able to eliminate `.java` files in your extension that were
originally copied from jastaddj. For example:

  * The JSR308 extension originally copied beaver .java files from JastAddJ,
    but they could now be compiled directly using the source code in JastAddJ.
In the build file, a path to JastAddJ/src/java was added, and the copying of
files was removed. See [these changes to the build file][15] for details.
  * The JSR308 extension originally had its own copy of JastAddJ's main program
    `JavaCompiler` (called  JavaJSR308Compiler). This main program could now be
eliminated from the extension, using the JastAddJ version directly. See [this
commit][16] for details. 

May 19, 2013: Renamed AST children
----------------------------------

**JastAddJ commit [93e8ce9][17]** (Refactoring)

In this commit, some AST children were renamed:

  * `SuperClassAccess` was renamed to `SuperClass`.
  * `SuperInterfaceId` was renamed to  `SuperInterface`.

### How to adapt:

  * Check your .ast files. If you have subclasses with children of these names,
    rename them.
  * Check your .jrag, .jadd files, and .java files. Rename accesses to the
    traversal methods of these children, e.g., `getSuperClassAccessOpt()`, and
`getSuperInterfaceIdList`.

See [example from NonNullChecker][18].

Apr 19, 2013: Reorganized source
--------------------------------

**JastAddJ commit [d732a34][19]** (Reorganizing source tree)

In a number of commits, between April 15 and April 19, 2013, ending with commit
[d732a34][20], the source code of JastAddJ was reorganized, and support for
Java6 and Java7 was added.

The previous structure looked like this:
 
    JastAddJ/
        Java1.4Frontend/
            scanner/*.flex
            parser/*.parser
            *.ast
            *.jrag *.jadd
            JavaChecker.java
            tools/*.jar
        Java1.4Backend/
            ...
            JavaCompiler.java
        Java1.5Frontend/ ...
        Java1.5Backend/ ...
        ...

The new structure looks like this:

    jastaddj/
        java4/
            scanner/*.flex
            parser/*.flex
            grammar/*.ast
            frontend/*.jrag *.jadd
        java5/ ...
        java6/ ...
        java7/ ...
        src/
           frontend/org/jastadd/jastaddj/JavaChecker.java
           backend/org/jastadd/jastaddj/JavaCompiler.java
           ...
        tools/*.jar
        ...

In addition, some scanner and parser files were renamed to use uniform
camelCase naming conventions.

Additionally, the build files were updated. Previously, there was one build
file for Java1.4Frontend, Java1.4Backend, Java1.5Frontend, and Java1.5Backend.
Now, there is just one build file for each Java version: java4, java5, java6,
and java7, each with separate targets for frontend and backend.

### How do adapt:

Update your build file to the new structure. See [example from JSR308][21]

Jun 29, 2012: Compile-time error messages updated
-------------------------------------------------

**JastAddJ commit [f9b40d3][22]** (Made field and variable initializer type
errors more verbose)

Since this commit, some compile-time error messages are changed.

### Only some extensions are affected:

For example, if you have tests that check the contents of compile-time error
messages, these might fail.

### How to adapt:

Update your test oracles for the failing tests. See [example from
NonNullChecker][23].

Dec 8, 2011: Renamed beaver.jar to beaver-ant.jar
-------------------------------------------------

**JastAddJ commit [5da7c86][24]** (Added missing beaver jar. Generate formal
type specifiers in method signature.)

Since this commit, `beaver.jar` (in the tools dir) has been renamed to
`beaver-ant.jar`.

### How to adapt:

Update your build file. See [example from JSR308][25]

[1]: https://bitbucket.org/jastadd/jastaddj/issues
[2]: https://bitbucket.org/jastadd/jastaddj/commits/39f7c6a1ff8a16f21834c448eac0b85fe82cf790
[3]: https://bitbucket.org/jastadd/jastaddj-nonnullchecker/commits/28ff81e7b4ca6384b1b6b0c6ac89f0d4c991f0b2
[4]: https://bitbucket.org/jastadd/jastaddj/commits/046b7ee395c4d2fa7725ea418f8b2775d9e6a075
[5]: https://bitbucket.org/jastadd/jastaddj/commits/450a43090045e8882121fd45b60d45b3f255a25d
[6]: https://bitbucket.org/jastadd/jastaddj/commits/f5da6105b14f5d437272bf42b332f7f6c2aec52f
[7]: https://bitbucket.org/jastadd/jastaddj-nonnullchecker/commits/486af5e924e7e9dd1243b19ad8a77afb6125c0d8
[8]: https://bitbucket.org/jastadd/jastaddj/commits/f5da6105b14f5d437272bf42b332f7f6c2aec52f
[9]: https://bitbucket.org/jastadd/jastaddj/commits/65be7b95b7de87c886d3b9676fd5487916f9f1f1
[10]: https://bitbucket.org/jastadd/jastaddj-jsr308/commits/534a4dc0e4ee357d30782979ae541e4305c0b794
[11]: https://bitbucket.org/jastadd/jastaddj/commits/a1d35760b7f497b3e8fc13b1363886909314a405
[12]: https://bitbucket.org/jastadd/jastaddj/commits/2fdb430c7d3d431e6fd0b1ffc8bf659cb1576b01
[13]: https://bitbucket.org/jastadd/jastaddj/commits/a1d35760b7f497b3e8fc13b1363886909314a405
[14]: https://bitbucket.org/jastadd/jastaddj/src/6326d674d672aa17f089da29b121324a092426c5/java7/build.xml
[15]: https://bitbucket.org/jastadd/jastaddj-jsr308/commits/8f9e39e6fec3268c6b1efd2c62be78f8ba7ce4c8#chg-build.xml
[16]: https://bitbucket.org/jastadd/jastaddj-jsr308/commits/d2c9bc274c357e9add3a0eb0bce5bc08fe4ac915
[17]: https://bitbucket.org/jastadd/jastaddj/commits/93e8ce9ac4fca3a9608929fdd500ec527984798b
[18]: https://bitbucket.org/jastadd/jastaddj-nonnullchecker/commits/3a249e8f80ce00211fcb841f4b4b75152e3d0b86
[19]: https://bitbucket.org/jastadd/jastaddj/commits/d732a3437d9e0f683bde9b8c4af12d0d09c0e702
[20]: https://bitbucket.org/jastadd/jastaddj/commits/d732a3437d9e0f683bde9b8c4af12d0d09c0e702
[21]: https://bitbucket.org/jastadd/jastaddj-jsr308/commits/2057ec01a92ad6679c825a9a94572615c54c18ab
[22]: https://bitbucket.org/jastadd/jastaddj/commits/f9b40d3b42e78c26252a33605ef808eb9a8fccc0
[23]: https://bitbucket.org/jastadd/jastaddj-nonnullchecker/commits/3b1a014c004cc041d8c4750157e3001fa5722942
[24]: https://bitbucket.org/jastadd/jastaddj/commits/5da7c86487f452b2753456473c0001a2201f4bbf
[25]: https://bitbucket.org/jastadd/jastaddj-jsr308/commits/1e423876a7826a7b2753b12a92272099737dadcc
[26]: https://bitbucket.org/jastadd/jastaddj/commits/f518c1e12452ee7ebb175afacbc628e717e07e5c
[27]: https://bitbucket.org/jastadd/jastaddj/commits/c5728657928539e49e9a45942a623ca7711a85f8
[28]: https://bitbucket.org/jastadd/jastaddj-nonnullchecker/commits/1afb20532a52d9f596872a0acf34ac23524d81a8
[29]: https://bitbucket.org/jastadd/jastaddj/commits/7e1d8efbb0e2091aea616e433451cf0e969be94e
