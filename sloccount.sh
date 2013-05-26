#!/bin/bash

# Counts number of lines in all JastAddJ modules
# requires the sloccount program

for module in java*; do
	echo $module
	tempfile=files-$$.tmp

	printf "  grammar lines: "
	find $module -name '*.ast' > $tempfile
	java_count -f $tempfile | tail -n 1

	printf "  jrag lines: "
	find $module -name '*.jadd' > $tempfile
	find $module -name '*.jrag' >> $tempfile
	java_count -f $tempfile | tail -n 1

	printf "  scanner lines: "
	find $module -name '*.flex' > $tempfile
	numfiles=`wc -l $tempfile | awk '{print $1}'`
	if [ "$numfiles" -gt "0" ]; then
		wc -l `find $module -name '*.flex'` | awk '{print $1}' | tail -n 1
	else
		printf "0\n"
	fi

	printf "  parser lines: "
	find $module -name '*.parser' > $tempfile
	java_count -f $tempfile | tail -n 1
	rm $tempfile
done
