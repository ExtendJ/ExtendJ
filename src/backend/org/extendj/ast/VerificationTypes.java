/* Copyright (c) 2017, Jesper Öqvist
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

public interface VerificationTypes {
  int ITEM_Top = 0;
  int ITEM_Integer = 1;
  int ITEM_Float = 2;
  int ITEM_Long = 4;
  int ITEM_Double = 3;
  int ITEM_Null = 5;
  int ITEM_UninitializedThis = 6;
  int ITEM_Object = 7;

  VerificationType TOP = new VerificationType() {
    {
      setSupertype(this);
    }
    @Override
    public int variableSize() {
      return 1;
    }
    @Override
    public String toString() {
      return "top";
    }
    @Override
    public boolean instanceOf(VerificationType type) {
      return this == type;
    }
    @Override
    public void emit(Attribute attr, ConstantPool cp) {
      attr.u1(ITEM_Top);
    }
  };

  VerificationType INT = new VerificationType(TOP) {
    @Override
    public int variableSize() {
      return 1;
    }
    @Override
    public void emit(Attribute attr, ConstantPool cp) {
      attr.u1(ITEM_Integer);
    }
    @Override
    public String toString() {
      return "int";
    }
  };

  VerificationType FLOAT = new VerificationType(TOP) {
    @Override
    public int variableSize() {
      return 1;
    }
    @Override
    public void emit(Attribute attr, ConstantPool cp) {
      attr.u1(ITEM_Float);
    }
    @Override
    public String toString() {
      return "float";
    }
  };

  VerificationType UNINITIALIZED_THIS = new VerificationType(TOP) {
    @Override
    public int variableSize() {
      return 1;
    }
    @Override
    public void emit(Attribute attr, ConstantPool cp) {
      attr.u1(ITEM_UninitializedThis);
    }
    @Override
    public String toString() {
      return "uninitializedThis";
    }
  };

  VerificationType LONG = new VerificationType(TOP, true) {
    @Override
    public int variableSize() {
      return 2;
    }
    @Override
    public void emit(Attribute attr, ConstantPool cp) {
      attr.u1(ITEM_Long);
    }
    @Override
    public String toString() {
      return "long";
    }
  };

  VerificationType DOUBLE = new VerificationType(TOP, true) {
    @Override
    public int variableSize() {
      return 2;
    }
    @Override
    public void emit(Attribute attr, ConstantPool cp) {
      attr.u1(ITEM_Double);
    }
    @Override
    public String toString() {
      return "double";
    }
  };

  class JavaType extends VerificationType {
    private final String constantName;

    public JavaType(TypeDecl type, TypeDecl supertype) {
      this(type.constantPoolName(), supertype.verificationType());
    }

    protected JavaType(String typeDescriptor, VerificationType supertype) {
      super(supertype);
      this.constantName = typeDescriptor;
    }
    @Override
    public int variableSize() {
      return 1;
    }
    @Override
    public void emit(Attribute attr, ConstantPool cp) {
      attr.u1(ITEM_Object);
      attr.u2(cp.addClass(constantName));
    }
    @Override
    public String toString() {
      return constantName;
    }
  }

  VerificationType OBJECT = new JavaType("java/lang/Object", TOP);
  VerificationType THROWABLE = new JavaType("java/lang/Throwable", OBJECT);
  VerificationType STRING = new JavaType("java/lang/String", OBJECT);
  VerificationType CLASS = new JavaType("java/lang/Class", OBJECT);

  VerificationType NULL = new VerificationType(OBJECT) {
    @Override
    public boolean instanceOf(VerificationType type) {
      return !type.isTwoWord;
    }
    @Override
    public int variableSize() {
      return 1;
    }
    @Override
    public void emit(Attribute attr, ConstantPool cp) {
      attr.u1(ITEM_Null);
    }
    @Override
    public String toString() {
      return "null";
    }
  };

  class ArrayType extends VerificationType {
    private final String constantName;
    public ArrayType(TypeDecl type) {
      super(OBJECT);
      this.constantName = type.constantPoolName();
    }
    @Override
    public int variableSize() {
      return 1;
    }
    @Override
    public void emit(Attribute attr, ConstantPool cp) {
      attr.u1(ITEM_Object);
      attr.u2(cp.addClass(constantName));
    }
    @Override
    public String toString() {
      return constantName;
    }
  }
}
