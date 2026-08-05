// Package name used as a typename.
// An interface may not implement a package.
// See issues 192, 193.
// .result: COMPILE_FAIL
package org.extendj;

public interface Test extends org.extendj {
}
