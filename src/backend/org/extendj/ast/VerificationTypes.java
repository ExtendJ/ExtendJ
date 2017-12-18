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
	int ITEM_Uninitialized = 8;

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
    @Override
    public boolean sameType(VerificationType other) {
      return this == other;
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
    @Override
    public boolean sameType(VerificationType other) {
      return this == other;
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
    @Override
    public boolean sameType(VerificationType other) {
      return this == other;
    }
  };

	public static final VerificationType UNINITIALIZED = new VerificationType(TOP) {
		@Override
		public int variableSize() {
			return 1;
		}
		@Override
		public void emit(Attribute attr, ConstantPool cp) {
			throw new UnsupportedOperationException();
		}
		@Override
		public String toString() {
			return "uninitiailzed";
		}
    @Override
    public boolean sameType(VerificationType other) {
      return this == other;
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
    @Override
    public boolean sameType(VerificationType other) {
      return this == other;
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
    @Override
    public boolean sameType(VerificationType other) {
      return this == other;
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
    @Override
    public boolean sameType(VerificationType other) {
      return this == other;
    }
  };

	class Uninitialized extends VerificationType {
		private final int offset;

		public Uninitialized(int offset) {
			setSupertype(UNINITIALIZED);
			this.offset = offset;
		}

		@Override
		public int variableSize() {
			return 1;
		}

		@Override
		public void emit(Attribute attr, ConstantPool cp) {
			attr.u1(ITEM_Uninitialized);
			attr.u2(offset);
		}

		@Override
		public String toString() {
			return "uninitialized("+offset+")";
		}

    @Override
    public boolean sameType(VerificationType other) {
      if (this == other) {
        return true;
      }
      if (other instanceof Uninitialized) {
        Uninitialized o = (Uninitialized) other;
        return offset == o.offset;
      }
      return false;
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

    @Override
    public boolean sameType(VerificationType other) {
      if (this == other) {
        return true;
      }
      if (other instanceof JavaType) {
        JavaType o = (JavaType) other;
        return constantName.equals(o.constantName);
      }
      return false;
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
    @Override
    public boolean sameType(VerificationType other) {
      return this == other;
    }
  };

  class ArrayType extends VerificationType {
    private final String constantName;
    public ArrayType(TypeDecl type) {
      TypeDecl componentType = type.componentType();
      VerificationType componentVt = componentType.verificationType();
      TypeDecl sup = componentType.supertype();
      if (sup.isUnknown()) {
        setSupertype(OBJECT);
      } else {
        setSupertype(sup.arrayType().verificationType());
      }
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
    @Override
    public boolean sameType(VerificationType other) {
      if (this == other) {
        return true;
      }
      if (other instanceof ArrayType) {
        ArrayType o = (ArrayType) other;
        return constantName.equals(o.constantName);
      }
      return false;
    }
  }
}
