#!/bin/bash
# Re-generates the pretty printing aspect code.

set -eu

ASPECTGEN="../../aspectgen/aspectgen.jar"
if [ ! -e "$ASPECTGEN" ]; then
    echo "Error: $ASPECTGEN is missing!"
    exit 1
fi
for JAVA_VERSION in {4,5,7,8}; do
	java -jar "$ASPECTGEN" -aspect "Java${JAVA_VERSION}PrettyPrint" Header.tt \
		"Java${JAVA_VERSION}PrettyPrint.tt" > \
		"../java${JAVA_VERSION}/frontend/PrettyPrint.jadd"
done
