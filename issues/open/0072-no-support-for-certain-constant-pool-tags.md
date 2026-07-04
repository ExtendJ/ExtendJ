# No support for certain constant pool tags

The constant pool tags CONSTANT_MethodHandle, CONSTANT_MethodType and CONSTANT_InvokeDynamic, mentioned in [JVMS version 7, section 4.4](http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.4) are currently not supported in JastAddJ. For example, the BytecodeReader will not recognize these tags in a library class file. There are classfiles in Java 8 that uses these tags, so currently JastAddJ is not compatible with the Java 8 library.
