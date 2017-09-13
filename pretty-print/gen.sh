#!/bin/bash
# Re-generates the pretty printing aspect code.
# This script clones the aspectgen tool, builds it, and then runs it on the template files (.tt).

set -eu

if [ ! -d "aspectgen" ]; then
	echo "Cloning Aspect Generator..."
	git clone --recursive https://bitbucket.org/joqvist/aspectgen
fi

ASPECTGEN="aspectgen/aspectgen.jar"
if [ ! -e "aspectgen/aspectgen.jar" ]; then
	echo "Building Aspect Generator..."
	(cd aspectgen; gradle jar)
fi

if [ ! -e "$ASPECTGEN" ]; then
    echo "Error: $ASPECTGEN is missing!"
    exit 1
fi

for JAVA_VERSION in {4,5,7,8}; do
	echo "Generating Java ${JAVA_VERSION} pretty printing aspect..."
	java -jar "$ASPECTGEN" -aspect "Java${JAVA_VERSION}PrettyPrint" Header.tt \
		"Java${JAVA_VERSION}PrettyPrint.tt" > \
		"../java${JAVA_VERSION}/frontend/PrettyPrint.jadd"
done
