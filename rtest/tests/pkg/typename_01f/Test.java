// Package name used as a typename.
// A class may not extend a package.
// https://bitbucket.org/extendj/extendj/issues/192/missing-error-for-class-extending-package
// .result: COMPILE_FAIL
package org.extendj;

public class Test extends org.extendj {
}
