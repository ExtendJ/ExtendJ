#!/bin/bash

interrupted()
{
    exit $?
}

trap interrupted SIGINT

# Counts number of lines in all JastAddJ modules
# requires the sloccount program

tempfile=files-$$.tmp
echo "generic"
printf "    java: "
find src/frontend/org/jastadd/jastaddj/ -name '*.java' > $tempfile
find src/backend/org/jastadd/jastaddj/ -name '*.java' >> $tempfile
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
	all=0

	printf "    scanner: "
	if [ -d "$module/scanner" ]; then
		find $module/scanner -name '*.flex' > $tempfile
		lines=`lex_count -f $tempfile | tail -n 1`
	else
		lines=0
	fi
	scanner=`expr $scanner + $lines`
	all=`expr $all + $lines`
	echo $lines

	printf "    parser: "
	if [ -d "$module/parser" ]; then
		find $module -name '*.parser' > $tempfile
		lines=`java_count -f $tempfile | tail -n 1`
	else
		lines=0
	fi
	parser=`expr $parser + $lines`
	all=`expr $all + $lines`
	echo $lines

	printf "    grammar: "
	if [ -d "$module/parser" ]; then
		find $module -name '*.ast' > $tempfile
		lines=`java_count -f $tempfile | tail -n 1`
	else
		lines=0
	fi
	grammar=`expr $grammar + $lines`
	all=`expr $all + $lines`
	echo $lines

	printf "    frontend: "
	if [ -d "$module/frontend" ]; then
		find $module/frontend -name '*.jadd' > $tempfile
		find $module/frontend -name '*.jrag' >> $tempfile
		lines=`java_count -f $tempfile | tail -n 1`
	else
		lines=0
	fi
	frontend=`expr $frontend + $lines`
	all=`expr $all + $lines`
	echo $lines

	printf "    backend: "
	if [ -d "$module/backend" ]; then
		find $module/backend -name '*.jadd' > $tempfile
		find $module/backend -name '*.jrag' >> $tempfile
		lines=`java_count -f $tempfile | tail -n 1`
	else
		lines=0
	fi
	backend=`expr $backend + $lines`
	all=`expr $all + $lines`
	echo $lines

	echo "    all: $all"
done

echo "totals:"
echo "    scanner: $scanner"
echo "    parser: $parser"
echo "    grammar: $grammar"
echo "    frontend: $frontend"
echo "    backend: $backend"
echo "    all:" `expr $grammar + $frontend + $backend + $scanner + $parser`

rm $tempfile
