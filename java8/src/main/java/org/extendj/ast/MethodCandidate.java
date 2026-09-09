// SPDX-License-Identifier: BSD-3-Clause
package org.extendj.ast;

import java.util.Objects;

public class MethodCandidate {
  public final MethodDecl decl;
  public final BoundSet bounds;

  public static final MethodCandidate UNRESOLVED = new MethodCandidate(null, new BoundSet((Expr) null));

  public MethodCandidate(MethodDecl decl, BoundSet bounds) {
    this.decl = decl;
    this.bounds = bounds;
  }

  @Override
  public boolean equals(Object o) {
    // The bound set is a pure inference product based on the declaration
    // and should not factor into equality comparison.
    if (!(o instanceof MethodCandidate)) return false;
    MethodCandidate that = (MethodCandidate) o;
    return Objects.equals(decl, that.decl);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(decl);
  }
}
