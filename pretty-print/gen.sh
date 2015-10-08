#!/bin/bash

set -eu

cp ../../aspectgen/aspectgen.jar .
for JAVA_VERSION in {4,5,7,8}; do
	java -jar aspectgen.jar -aspect "Java${JAVA_VERSION}PrettyPrint" Header.tt \
		"Java${JAVA_VERSION}PrettyPrint.tt" > \
		"../java${JAVA_VERSION}/frontend/PrettyPrint.jadd"
done
