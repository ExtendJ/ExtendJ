// SPDX-License-Identifier: BSD-3-Clause
package org.extendj.ast;

import java.util.*;

/**
 * Describes a function type by pointing to a method declaration
 * representing the single abstract method of a functional interface
 * (JLS SE8 §9.8).
 *
 * <p>The {@code method} field can point to {@code unknownMethod} if
 * the attribute producing this {@code FunctionDescriptor} failed to
 * find a single abstract method or if a non-wildcard parameterization
 * could not be created.
 */
class FunctionDescriptor {
  public final java.util.List<TypeDecl> throwsList;
  public final MethodDecl method;
  public final InterfaceDecl fromInterface;

  public FunctionDescriptor(InterfaceDecl fromInterface, MethodDecl method, java.util.List<TypeDecl> throwsList) {
    this.fromInterface = fromInterface;
    Optional<MethodDecl> nw = method.nonWildcardParameterization();
    this.method = nw.isPresent() ? nw.get() : method.unknownMethod();
    this.throwsList = throwsList;
  }

  public boolean isGeneric() {
    return method.isGeneric();
  }

  public InterfaceDecl fromInterface() {
    return this.fromInterface;
  }

  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append(method.toString());
    str.append(" throws ");
    if (throwsList.size() > 0) {
      str.append(throwsList.get(0).typeName());
      for (int i = 1; i < throwsList.size(); i++) {
        str.append(", " + throwsList.get(i).toString());
      }
    }
    return str.toString();
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof FunctionDescriptor)) {
      return false;
    }
    FunctionDescriptor o = (FunctionDescriptor) other;
    return fromInterface == o.fromInterface
        && method == o.method
        && throwsList.equals(o.throwsList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromInterface, method.hashCode());
  }
}
