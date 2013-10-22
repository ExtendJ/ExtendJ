#!/bin/bash

if [ $# -lt "1" ]; then
    echo "Usage: $0 <Class Name> [Tag]"
	echo "<...> required"
	echo "[...] optional"
    exit 1
fi

echo "Checking $1"

SRCDIR=../jjtest/tests/$1
SRC=$SRCDIR/Test.java

if [ ! -f "$SRC" ]; then
	echo "Error: $SRC does not exist!"
	exit 1
fi

if [ ! -d "tmp" ]; then
	mkdir "tmp"
fi

N=Test

ASM=../ow2-asm
CP=$ASM/output/build/tmp

# build ASM if needed
if [ ! -f "$CP/org/objectweb/asm/util/CheckClassAdapter.class" ]; then
	ant -f $ASM/build.xml compile
fi

JASTADDJ=${JASTADDJ:-jastaddj.jar}

if [ ! -z "$JAVAC" ]; then
	javac -d tmp $SRC 2>&1 | tee tmp/$N.log
else
	java -jar "$JASTADDJ" -cp $SRCDIR -d tmp $SRC 2>&1 | tee tmp/$N.log
fi

javap -private -verbose -c -classpath tmp $N > tmp/$N.bytecode

java -cp $CP org.objectweb.asm.util.CheckClassAdapter tmp/$N.class &> tmp/$N.verifier

if [ -z "$NOEXEC" ]; then
	java -cp tmp $N
fi
