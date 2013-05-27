#!/bin/bash

# Counts number of lines in all JastAddJ modules
# requires the sloccount program

tempfile=files-$$.tmp
echo "generic"
printf "    java: "
find src/java/org/jastadd/jastaddj/ -name '*.java' > $tempfile
java_count -f $tempfile | tail -n 1

echo "generated"
printf "    java: "
find src/gen/ -name '*.java' > $tempfile
java_count -f $tempfile | tail -n 1

grammar=0
frontend=0
backend=0
scanner=0
parser=0

for module in java*; do
	echo $module
	moduletot=0

	printf "    scanner: "
	find $module -name '*.flex' > $tempfile
	numfiles=`wc -l $tempfile | awk '{print $1}'`
	if [ "$numfiles" -gt "0" ]; then
		num=`wc -l $(find $module -name '*.flex') | awk '{print $1}' | tail -n 1`
	else
		num=0
	fi
	scanner=`expr $scanner + $num`
	moduletot=`expr $moduletot + $num`
	echo $num

	printf "    parser: "
	find $module -name '*.parser' > $tempfile
	num=`java_count -f $tempfile | tail -n 1`
	parser=`expr $parser + $num`
	moduletot=`expr $moduletot + $num`
	echo $num

	printf "    grammar: "
	find $module -name '*.ast' > $tempfile
	num=`java_count -f $tempfile | tail -n 1`
	grammar=`expr $grammar + $num`
	moduletot=`expr $moduletot + $num`
	echo $num

	printf "    frontend: "
	find $module/frontend -name '*.jadd' > $tempfile
	find $module/frontend -name '*.jrag' >> $tempfile
	num=`java_count -f $tempfile | tail -n 1`
	frontend=`expr $frontend + $num`
	moduletot=`expr $moduletot + $num`
	echo $num

	printf "    backend: "
	if [ -d "$module/backend" ]; then
		find $module/backend -name '*.jadd' > $tempfile
		find $module/backend -name '*.jrag' >> $tempfile
		num=`java_count -f $tempfile | tail -n 1`
	else
		num=0
	fi
	backend=`expr $backend + $num`
	moduletot=`expr $moduletot + $num`
	echo $num

	echo "    all: $moduletot"
done

echo "totals:"
echo "    scanner: $scanner"
echo "    parser: $parser"
echo "    grammar: $grammar"
echo "    frontend: $frontend"
echo "    backend: $backend"
echo "    all: " `expr $grammar + $frontend + $backend + $scanner + $parser`

rm $tempfile
