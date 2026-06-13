// Package name used as a typename.
// An interface may not implement a package.
// https://bitbucket.org/extendj/extendj/issues/192/missing-error-for-class-extending-package
// https://bitbucket.org/extendj/extendj/issues/193/missing-error-for-class-implementing
// .result: COMPILE_FAIL
package org.extendj;

public interface Test extends org.extendj {
}
