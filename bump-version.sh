#!/bin/sh

VERSION=`git describe`

# bump version string using git describe
echo "Bumping JastAddJ version string..."
echo "version=$VERSION"
echo "version=$VERSION" > src/res/JastAddJ.properties
