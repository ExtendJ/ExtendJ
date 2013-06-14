#!/bin/bash

if [ $# -lt "1" ]; then
    echo "Usage: $0 <Class Name> [Tag]"
	echo "<...> required"
	echo "[...] optional"
    exit 1
fi

echo "Checking $1"

SRC=test/$1.java

if [ ! -f "$SRC" ]; then
	echo "Error: $SRC does not exist!"
	exit 1
fi

if [ ! -z "$2" ]; then
	N=$1.$2
else
	N=$1
fi

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
	java -jar $JASTADDJ -d tmp $SRC 2>&1 | tee tmp/$N.log
fi

javap -verbose -c tmp/$1.class > tmp/$N.bytecode

if [ ! -z "$VERIFY" ]; then
	java -cp $CP org.objectweb.asm.util.CheckClassAdapter tmp/$1.class &> tmp/$N.verifier
else
	java -cp $CP org.objectweb.asm.util.Textifier tmp/$1.class &> tmp/$N.text
fi

if [ -z "$NOEXEC" ]; then
	java -cp tmp $1
fi
