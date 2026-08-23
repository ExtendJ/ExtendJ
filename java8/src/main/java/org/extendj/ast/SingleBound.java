// SPDX-License-Identifier: BSD-3-Clause
package org.extendj.ast;

import java.util.*;

/**
 * A bound on a single inference variable (as opposed to a CaptureBound which involves multiple variables).
 */
public class SingleBound implements Bound {
  public Kind kind;
  public TypeVariable alpha;
  public TypeDecl type;

  public SingleBound(Kind k, TypeVariable a, TypeDecl T) {
    kind = k;
    alpha = a;
    type = T;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SingleBound)) return false;
    SingleBound that = (SingleBound) o;
    return kind == that.kind && alpha == that.alpha && type == that.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(kind, alpha, type);
  }

  @Override
  public String toString() {
    switch (kind) {
      case EQUAL:
        return String.format("%s = %s", alpha.typeName(), type.typeName());
      case UPPER:
        return String.format("%s <: %s", alpha.typeName(), type.typeName());
      case LOWER:
        return String.format("%s :> %s", alpha.typeName(), type.typeName());
    }
    return kind.toString();
  }
}
