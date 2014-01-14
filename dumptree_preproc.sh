#!/bin/bash

if [ $# -lt "1" ]; then
    echo "Usage: $0 <Class Name>"
	echo "<...> required"
    exit 1
fi

java -cp ant-bin org.jastadd.jastaddj.JavaDumpTreePreproc test/$1.java
