#!/bin/bash

if [ $# -lt "1" ]; then
    echo "Usage: $0 <Class Name>"
	echo "<...> required"
    exit 1
fi

java -cp jastaddj.jar org.jastadd.jastaddj.JavaDumpFrontendErrors test/$1.java
