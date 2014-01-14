JastAddJ
========

The JastAdd extensible Java compiler.

License & Copyright
-------------------

* Copyright (c) 2005-2008, Torbj&ouml;rn Ekman
* Copyright (c) 2005-2013, JastAddJ Committers

All rights reserved.

JastAddJ is covered by the Modified BSD License. The full license text is
distributed with this software. See the `LICENSE` file.

Tools Used
----------

JastAddJ uses these libraries:

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
* RagDoll R20120208, Copyright (c) 2011-2012 Jesper &Ouml;qvist. RagDoll is
  covered by the GNU General Public License Version 2, with the Classpath
Exception. See the file `licenses/RagDoll-GPL` for the full license text.

The only library used by JastAddJ at runtime is the Beaver runtime component
`beaver-rt.jar`.

Building
--------

JastAddJ is built using Apache Ant. Each module has it's own Ant script, and
there is a toplevel Ant script that contains targets to build JastAddJ with
support for various versions of Java.  The default target will build JastAddJ
for the highest supported Java version.

If you have Ant installed you can get a list of available build targets by
entering the following in a terminal:

    $ ant -p

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

JastAddJ is intended to be an extensible compiler, however right now we are
changing things in JastAddJ rapidly and breaking backward-compatibility.

The JastAddJ API up to version 7.1 was mostly much unchanged for several years.
Since version 7.1 though many things have changed in JastAddJ in order to
remove side effects, fix errors, and make the code more understandable. The
next release should be much more stable, but right now JastAddJ is changing
very much. Most of these non-compatible changes have happened since we moved
the main development code to bitbucket. What you see in the bitbucket
repository should be considered unstable.

[See the extension migration guide][1] for more information about migrating
an extension from an older version of JastAddJ to the latest development
version.

Debugging
---------

If JastAddJ should generate faulty bytecode there are a number of different
tools that can be used to diagnose the problem.

* `javap` comes with JDK
* `asm` can be downloaded http://asm.ow2.org/

`javap can be used to disassemble compiled bytecode:

    $ javap -verbose -c Test.class

ASM by OW2 Consortium can be used for advanced instrumentation and analysis
of bytecode. There is also a useful plugin for eclipse called
"Bytecode Outline" from OW2.

[1]: https://bitbucket.org/jastadd/jastaddj/src/HEAD/ExtensionMigrationGuide.md?at=master
