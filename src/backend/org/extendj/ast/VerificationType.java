/* Copyright (c) 2017, Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.extendj.ast;

/**
 * Verification types. The type hierarchy is coded using the instanceOf method.
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public abstract class VerificationType {

  private VerificationType supertype;
  public final boolean isTwoWord;

  protected VerificationType() {
    this(null, false);
  }

  protected VerificationType(VerificationType supertype) {
    this(supertype, false);
  }

  protected VerificationType(VerificationType supertype, boolean twoWord) {
    this.supertype = supertype;
    this.isTwoWord = twoWord;
  }

  protected void setSupertype(VerificationType parent) {
    this.supertype = parent;
  }

  /**
   * @return stack variable size for this verification type
   */
  abstract public int variableSize();

  /**
   * @return {@code true} if this type is assignable to the given type
   */
  public boolean instanceOf(VerificationType type) {
    return this == type || supertype.instanceOf(type);
  }

  /**
   * Finds the common supertype of two types (nearest common ancestor type).
   */
  public VerificationType nca(VerificationType other) {
    if (this == other) {
      return this;
    }
    if (other == VerificationTypes.NULL && this.instanceOf(VerificationTypes.OBJECT)) {
      return this;
    }
    if (this == VerificationTypes.NULL && other.instanceOf(VerificationTypes.OBJECT)) {
      return other;
    }
    VerificationType x = this;
    VerificationType y = other;
    while (x != VerificationTypes.TOP & y != VerificationTypes.TOP) {
      x = x.supertype;
      y = y.supertype;
    }
    VerificationType q = this;
    while (x != VerificationTypes.TOP) {
      x = x.supertype;
      q = q.supertype;
    }
    VerificationType p = other;
    while (y != VerificationTypes.TOP) {
      p = p.supertype;
      y = y.supertype;
    }
    while (p != VerificationTypes.TOP && q != VerificationTypes.TOP && p != q) {
      p = p.supertype;
      q = q.supertype;
    }
    return p;
  }

  abstract public void emit(Attribute attr, ConstantPool cp);

  /**
   * @return {@code true} if this type is the same type as the argument type.
   */
  abstract public boolean sameType(VerificationType other);
}
