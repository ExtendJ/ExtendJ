#!/bin/sh

# bump version strings using git describe
echo "Bumping JastAddJ version strings..."
echo "version=`git describe`" > src/res/JastAddJ.properties
echo "version.short=`git describe --abbrev=0`" >> src/res/JastAddJ.properties
echo "Don't forget to commit these changes!"
