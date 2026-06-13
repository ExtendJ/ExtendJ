// Package name used as a typename.
// A class may not implement a package.
// https://bitbucket.org/extendj/extendj/issues/193/missing-error-for-class-implementing
// .result: COMPILE_FAIL
package org.extendj;

public class Test implements org.extendj {
}
