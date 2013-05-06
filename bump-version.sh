#!/bin/sh

VERSION=`git describe`
SHORTVERSION=`git describe --abbrev=0`

# bump version strings using git describe
echo "Bumping JastAddJ version strings..."
echo "version=$VERSION"
echo "version=$VERSION" > src/res/JastAddJ.properties
echo "version.short=$SHORTVERSION"
echo "version.short=$SHORTVERSION" >> src/res/JastAddJ.properties
