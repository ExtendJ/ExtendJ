package org.extendj.ast;

import java.util.Objects;

public class MethodInferenceNode {
  public MethodDecl decl;
  public BoundSet bounds;

  public static final MethodInferenceNode UNRESOLVED = new MethodInferenceNode(null, new BoundSet(null));

  public MethodInferenceNode(MethodDecl decl, BoundSet bounds) {
    this.decl = decl;
    this.bounds = bounds;
  }

  @Override
  public boolean equals(Object o) {
    // The bound set is a pure inference product based on the declaration
    // and should not factor into equality comparison.
    if (!(o instanceof MethodInferenceNode)) return false;
    MethodInferenceNode that = (MethodInferenceNode) o;
    return Objects.equals(decl, that.decl);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(decl);
  }
}
