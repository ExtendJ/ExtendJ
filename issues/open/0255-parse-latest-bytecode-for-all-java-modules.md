# Parse latest bytecode for all Java modules

*ExtendJ 8.0.1-258-g9891139 Java SE 8*

Currently, ExtendJ only allows Java 6 bytecode when running the Java 5/6 modules, and Java 7 bytecode when running the Java 7 module. This means that the Java 5/6 builds must be run by a Java 6 VM, and the Java 7 build must be run by a Java 7 VM.

ExtendJ could instead allow the latest supported bytecode version (currently Java 8) in all modules. This would make it much easier to run the regression tests for the Java 5,6,7 modules, since they could be run by a Java 8 VM.
