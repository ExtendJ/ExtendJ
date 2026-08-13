// SPDX-License-Identifier: BSD-3-Clause
package org.extendj.ast;

import java.util.*;

class Bound {
  public enum Kind { UPPER, LOWER, EQUAL, CAPTURE }

  public Kind kind;
  public TypeVariable alpha;
  public TypeDecl type;

  public Bound(Kind k, TypeVariable a, TypeDecl T) {
    kind = k;
    alpha = a;
    type = T;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Bound)) return false;
    Bound that = (Bound) o;
    return kind == that.kind && alpha == that.alpha && type == that.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(kind, alpha, type);
  }
}

