#!/bin/bash

# Counts number of tokens in all JastAddJ modules
# requires the sloccount program

interrupted()
{
    exit $?
}

trap interrupted SIGINT

tempfile=files-$$.tmp

count_tokens()
{
	java -cp ant-bin org.jastadd.jastaddj.TokenCounter @$tempfile 2>/dev/null |cut -d ' ' -f 2
}

# exit the script if any command fails
set -e

echo "generic"
printf "    java: "
find src/frontend/org/jastadd/jastaddj/ -name '*.java' > $tempfile
find src/backend/org/jastadd/jastaddj/ -name '*.java' >> $tempfile
count_tokens

echo "generated"
printf "    java: "
find src/gen/ -name '*.java' > $tempfile
count_tokens

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
		tokens=`count_tokens`
	else
		tokens=0
	fi
	scanner=`expr $scanner + $tokens`
	all=`expr $all + $tokens`
	echo $tokens

	printf "    parser: "
	if [ -d "$module/parser" ]; then
		find $module -name '*.parser' > $tempfile
		tokens=`count_tokens`
	else
		tokens=0
	fi
	parser=`expr $parser + $tokens`
	all=`expr $all + $tokens`
	echo $tokens

	printf "    grammar: "
	if [ -d "$module/parser" ]; then
		find $module -name '*.ast' > $tempfile
		tokens=`count_tokens`
	else
		tokens=0
	fi
	grammar=`expr $grammar + $tokens`
	all=`expr $all + $tokens`
	echo $tokens

	printf "    frontend: "
	if [ -d "$module/frontend" ]; then
		find $module/frontend -name '*.jadd' > $tempfile
		find $module/frontend -name '*.jrag' >> $tempfile
		tokens=`count_tokens`
	else
		tokens=0
	fi
	frontend=`expr $frontend + $tokens`
	all=`expr $all + $tokens`
	echo $tokens

	printf "    backend: "
	if [ -d "$module/backend" ]; then
		find $module/backend -name '*.jadd' > $tempfile
		find $module/backend -name '*.jrag' >> $tempfile
		tokens=`count_tokens`
	else
		tokens=0
	fi
	backend=`expr $backend + $tokens`
	all=`expr $all + $tokens`
	echo $tokens

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
