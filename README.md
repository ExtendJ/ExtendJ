ExtendJ
========

The JastAdd extensible Java compiler.

License & Copyright
-------------------

* Copyright (c) 2005-2008, Torbjörn Ekman
* Copyright (c) 2005-2016, ExtendJ Committers

All rights reserved.

ExtendJ is covered by the Modified BSD License. The full license text is
distributed with this software. See the `LICENSE` file.

Tools Used
----------

ExtendJ uses these libraries:

* JastAdd2 R20130212, Copyright (c) 2005-2013, The JastAdd Team. JastAdd2 is
  covered by the Modified BSD License. See the file `licenses/JastAdd2-BSD` for
the full license text.
* Beaver 0.9.11, Copyright (c) 2003-2011 Alexander Demenchuk. Beaver is covered
  by the Modified BSD License. See the file `licenses/Beaver-BSD` for the full
license text.
* JFlex 1.4.3, Copyright (c) 1998-2009 Gerwin Klein. JFlex is covered by the
  GNU General Public License. See the file `licenses/JFlex-GPL` for the full
license text.
* JastAddParser, Copyright (c) 2005, The JastAdd Team. JastAddParser is covered
  by the Modified BSD License. See the file `licenses/JastAddParser-BSD` for
the full license text.
* RagDoll R20120208, Copyright (c) 2011-2012 Jesper Öqvist. RagDoll is
  covered by the GNU General Public License Version 2, with the Classpath
Exception. See the file `licenses/RagDoll-GPL` for the full license text.

The only library used by ExtendJ at runtime is the Beaver runtime component
`beaver-rt.jar`.

Building
--------

ExtendJ is built using Apache Ant or Gradle.

The Ant build has one Ant script for each module, with
top-level Ant script that contains targets to build ExtendJ with
support for various versions of Java.  The default target will build ExtendJ
for the highest supported Java version.

If you have Ant installed you can get a list of available build targets by
entering the following in a terminal:

    ant -p


To build the Java 8 version of ExtendJ with Ant, use the following command:

    ant clean java8 jar


The Gradle build is organized into subprojects for each supported Java version.

To build the Java 8 version of ExtendJ with Gradle, use the following command:

    gradle clean :java8:jar


Running
-------

Usage:

    java JavaCompiler <options> <source files>
      -verbose                  Output messages about what the compiler is doing
      -classpath <path>         Specify where to find user class files
      -sourcepath <path>        Specify where to find input source files
      -bootclasspath <path>     Override location of bootstrap class files
      -extdirs <dirs>           Override location of installed extensions
      -d <directory>            Specify where to place generated class files
      -nowarn                   Disable warning messages
      -help                     Print a synopsis of standard options
      -version                  Print version information

Extensions
----------

ExtendJ is intended to be an extensible compiler. By using JastAdd to build ExtendJ,
you can modify nearly anything through an extension. Since nearly every core part of
the compiler is extendable, extensions become fragile: nearly any change to the
core compiler can affect extensions.

ExtendJ is also constantly evolving. We frequently redesign parts of the
compiler to fix bugs and improve maintainability.  Any bug fix in ExtendJ has
the potential to affect extensions.

If you want to develop an extension for ExtendJ, we recommend that you stick to
using a single commit of ExtendJ to build your extension. Only update to a new
version of ExtendJ if you have a good test suite that can ensure that the
extension keeps working.

[See the extension migration guide][1] for more information about migrating
an extension from an older version of ExtendJ to the latest development
version.

Development
-----------

For new ExtendJ developers, we recommend that you look at the ExtendJ website (extendj.org),
in particular the [Getting Started Guide][4].

Some useful scripts for ExtendJ development can be found at [the JJScripts
repository][2].

### Coding Style

If you want to submit a patch to ExtendJ, please follow our [code style guide][3].

### Debugging

In the case that ExtendJ generates faulty
bytecode there are a number of different tools that can be used to diagnose the
problem.

* `javap` comes with the JDK
* `asm` can be downloaded from http://asm.ow2.org/

`javap` can be used to disassemble compiled bytecode:

    $ javap -verbose -c Test.class

ASM by OW2 Consortium can be used for advanced instrumentation and analysis
of bytecode. There is also a useful plugin for eclipse called
"Bytecode Outline" from OW2.

[1]: https://bitbucket.org/extendj/extendj/src/HEAD/ExtensionMigrationGuide.md?at=master
[2]: https://bitbucket.org/joqvist/jjscripts
[3]: http://extendj.org/code_style.html
[4]: http://extendj.org/getting_started.html
