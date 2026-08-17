// SPDX-License-Identifier: BSD-3-Clause
package org.extendj.ast;

import java.util.*;

/**
 * Represents a capture bound {@code G<β1, ..., βn> = capture(G<A1, ..., An>)}
 * where {@code baseType = G}, {@code lhs = β1, ..., βn}, and {@code rhs = A1, ..., An}.
 */
class CaptureBound implements Bound {
  public final TypeDecl baseType; // Parameterized type being captured
  public final java.util.List<TypeVariable> lhs = new ArrayList<>();
  public final java.util.List<TypeDecl> rhs = new ArrayList<>();

  public CaptureBound(TypeDecl type) {
    baseType = type;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append(baseType.name()).append("<");
    String sep = "";
    for (TypeVariable it : lhs) {
      str.append(sep).append(it.typeName());
      sep = ", ";
    }
    str.append("> → capture(").append(baseType.name()).append("<");
    sep = "";
    for (TypeDecl it : rhs) {
      str.append(sep).append(it.typeName());
      sep = ", ";
    }
    str.append(">)");
    return str.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof CaptureBound)) return false;
    CaptureBound that = (CaptureBound) o;
    return baseType == that.baseType
      && Objects.equals(lhs, that.lhs) && Objects.equals(rhs, that.rhs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseType, lhs, rhs);
  }
}
