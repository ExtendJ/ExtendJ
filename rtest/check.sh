#!/bin/bash

# Check single test compilation status: javac vs extendj.
# Usage:
#   ./check.sh lex/num_01p
#   ./check.sh tests/lex/num_01p
#   DEBUG=y ./check.sh tests/lex/num_01p

TESTROOT="tests"
TMPDIR="scratch"

if [ -d "$TESTROOT/$1" ]; then
  TEST="$TESTROOT/$1"
elif [ -d "$1" ]; then
  TEST="$1"
else
  echo "Test directory not found: $1"
  echo "usage: check TESTDIR"
  exit 1
fi

JVMFLAGS=""
if [[ $DEBUG =~ ^[yY] ]]; then
  JVMFLAGS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005"
fi

if [ ! -d "$TEST" ]; then
  echo "ERROR: not a directory: $TEST"
  exit 1
fi

if [ -d "$TMPDIR" ]; then
  rm -r "$TMPDIR"
fi
mkdir -p $TMPDIR

: "${SOURCE:=8}"
: "${TARGET:=8}"

JAVAC="javac"
EXTENDJ="java $JVMFLAGS -jar extendj.jar"
$JAVAC -source $SOURCE -target $TARGET -d tmp $TEST/Test.java 2>javac.err
ref=$?
if [ "$ref" == "0" ]; then
  if [ -s javac.err ]; then
    echo -e "\e[1mjavac   \e[46mWARN\e[0m"
  else
    echo -e "\e[1mjavac   \e[42mPASS\e[0m"
  fi
else
  echo -e "\e[1mjavac   \e[41mFAIL\e[0m"
fi

rm -r "$TMPDIR"
mkdir -p $TMPDIR

$EXTENDJ -d tmp $TEST/Test.java 2>exj.err
if [ "$?" == "0" ]; then
  if [ -s exj.err ]; then
    echo -e "\e[1mExtendJ \e[46mWARN\e[0m"
  else
    echo -e "\e[1mExtendJ \e[42mPASS\e[0m"
  fi
else
  echo -e "\e[1mExtendJ \e[41mFAIL\e[0m"
  if [ "$ref" == "0" ]; then
    cat exj.err
  fi
fi
