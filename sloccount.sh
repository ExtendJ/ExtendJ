#!/bin/bash

# Counts number of lines in all JastAddJ modules
# requires the sloccount program

tempfile=files-$$.tmp
echo "generic"
printf "  java: "
find src/java/org/jastadd/jastaddj/ -name '*.java' > $tempfile
java_count -f $tempfile | tail -n 1

echo "generated"
printf "  java: "
find src/gen/ -name '*.java' > $tempfile
java_count -f $tempfile | tail -n 1

for module in java*; do
	echo $module

	printf "  grammar: "
	find $module -name '*.ast' > $tempfile
	java_count -f $tempfile | tail -n 1

	printf "  jrag: "
	find $module -name '*.jadd' > $tempfile
	find $module -name '*.jrag' >> $tempfile
	java_count -f $tempfile | tail -n 1

	printf "  scanner: "
	find $module -name '*.flex' > $tempfile
	numfiles=`wc -l $tempfile | awk '{print $1}'`
	if [ "$numfiles" -gt "0" ]; then
		wc -l `find $module -name '*.flex'` | awk '{print $1}' | tail -n 1
	else
		printf "0\n"
	fi

	printf "  parser: "
	find $module -name '*.parser' > $tempfile
	java_count -f $tempfile | tail -n 1
done

rm $tempfile
