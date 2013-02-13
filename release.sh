#!/bin/sh

if [ $# -lt "1" ]; then
    echo "Usage: $0 VERSION"
    exit 1
fi

# set version string
VERSION=$1

echo "# Follow these steps to release JastAddJ-$VERSION:"
echo
echo "# 1. Create the source-release zip file"
echo "ant release -Dversion=$VERSION"
echo
echo "# 2. Create a new dir at jastadd.org"
echo "ssh login.cs.lth.se \"mkdir /cs/jastadd/releases/jastaddj/$VERSION\""
echo
echo "# 3.1 Upload the zip files and appropriate documentation to jastadd.org"
echo "scp jastaddj-$VERSION.zip \\"
echo "    login.cs.lth.se:/cs/jastadd/releases/jastaddj/$VERSION"
echo
echo "# 3.2 Make sure the new files have group write permission"
echo "ssh login.cs.lth.se \"chmod -R g+w /cs/jastadd/releases/jastaddj/$VERSION\""
echo
echo # Check that it works
echo ------------------------------------------------------
echo Browse to the website and check that everything works.
echo Then ant clean and commit.
echo ------------------------------------------------------
echo
echo "# 4. Tag in SVN"
echo "svn copy http://svn.cs.lth.se/svn/jastadd-oxford/projects/trunk/JastAddJ \\"
echo "    http://svn.cs.lth.se/svn/jastadd-oxford/projects/tags/JastAddJ/${VERSION} \\"
echo "    -m \"Release ${VERSION} of JastAddJ\""
