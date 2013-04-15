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

Building
--------

JastAddJ is built using Apache Ant. Each module has it's own Ant
script, and there is a toplevel Ant script that contains targets to build
JastAddJ with support for various versions of Java.  The default target will
build JastAddJ for the highest supported Java version.

If you have Ant installed you can get a list of available build targets
by entering the following in a terminal:

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
      -help                     Print a synopsis of standard options
      -version                  Print version information

