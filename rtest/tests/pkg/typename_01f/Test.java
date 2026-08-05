// Package name used as a typename.
// A class may not extend a package.
// See issue 192.
// .result: COMPILE_FAIL
package org.extendj;

public class Test extends org.extendj {
}
