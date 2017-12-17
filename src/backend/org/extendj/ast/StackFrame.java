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

import java.util.ArrayList;
import java.util.List;

/**
 * Tracks the type of local variables and operand stack entries in bytecode.
 *
 * <p>Stack frames are used to compute the stack map frames attributes for Java 6+ bytecode.
 *
 * <p>Wide types (long and double) are stored in the local variable map by adding an
 * extra TOP entry after the wide type. This is to make handling of local indexes simpler.
 * On the stack no extra TOP entry is added after a wide type.
 *
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public class StackFrame {

  private static final VerificationType EMPTY_LOCAL = VerificationTypes.TOP;

  private final List<VerificationType> locals = new ArrayList<VerificationType>(8);
  private int maxLocals = 0;

  private final List<VerificationType> stack = new ArrayList<VerificationType>(8);
  private int maxStack = 0;

  /**
   * Size of stack in words.
   */
  private int stackSize = 0;

  /**
   * Top of stack (items).
   */
  private int top = 0;

  /**
   * Bytecode offset for this stack map frame.
   */
  public int offset;

  /** Previous frame used to generate a diff stack frame in bytecode. */
  public StackFrame prevFrame = null;

  /**
   * Initialize empty stack frame.
   */
  public StackFrame() {
  }

  /**
   * Copy constructor.
   * @param other stack frame to copy
   */
  public StackFrame(StackFrame other) {
    copy(other);
  }

  /**
   * Inspect stack types.
   *
   * @return Type of item at offset from stack top.
   */
  private VerificationType peek(int offset) {
    if (top - offset - 1 < 0) {
      throw new Error("Operand stack underrun.");
    }
    return stack.get(top - offset - 1);
  }

  public void pop() {
    if (top == 0) {
      throw new Error("Operand stack underrun.");
    } else {
      stackSize -= stack.get(top - 1).variableSize();
      stack.remove(--top);
    }
  }

  /**
   * Pop multiple elements off the stack.
   */
  public void pop(int num) {
    for (int i = 0; i < num; ++i) {
      pop();
    }
  }

  public VerificationType getLocal(int index) {
    return locals.get(index);
  }

  public void setLocal(int index, VerificationType type) {
    while (index > maxLocals) {
      locals.add(EMPTY_LOCAL);
      maxLocals += 1;
    }
    if (index == maxLocals) {
      locals.add(type);
      maxLocals += 1;
    } else {
      locals.set(index, type);
    }
  }

  /**
   * Push an element on the stack.
   */
  public void push(VerificationType type) {
    stack.add(type);
    afterPush(type);
  }

  private void afterPush(VerificationType type) {
    top += 1;
    stackSize += type.variableSize();
    if (stackSize > maxStack) {
      maxStack = stackSize;
    }
  }

  public void stackDiff(int size) {
    if (stackSize + size > maxStack) {
      maxStack = stackSize + size;
    }
  }

  /**
   * @return maximum stack size of frame
   */
  public int maxStack() {
    return maxStack;
  }

  /**
   * @return number of allocated locals in this frame.
   */
  public int maxLocals() {
    return maxLocals;
  }

  /**
   * Duplicate top of stack.
   */
  public void dup() {
    VerificationType topType = peek(0);
    push(topType);
  }

  /**
   * Duplicate top of stack and insert one element down.
   */
  public void dup_x1() {
    VerificationType topType = peek(0);
    stack.add(top - 2, topType);
    afterPush(topType);
  }

  /**
   * Duplicate top of stack and insert two elements down.
   */
  public void dup_x2() {
    VerificationType topType = peek(0);
    stack.add(top - 3, topType);
    afterPush(topType);
  }

  /**
   * Swap top two words on the stack.
   *
   * <p>The two top elements must be one-word types.
   */
  public void swap() {
    VerificationType a = peek(0);
    VerificationType b = peek(1);
    stack.set(top - 1, b);
    stack.set(top - 2, a);
  }

  /**
   * Allocate a local variable without popping the stack (used for parameter initialization).
   */
  public void allocate(int index, VerificationType type) {
    setLocal(index, type);
    if (type.isTwoWord) {
      setLocal(index + 1, VerificationTypes.TOP);
    }
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (VerificationType type : locals) {
      sb.append(type.toString());
      sb.append(" ");
    }
    sb.append("::");
    for (VerificationType type : stack) {
      sb.append(" ");
      sb.append(type.toString());
    }
    return sb.toString();
  }

  int assignedLocals() {
    for (int i = maxLocals - 1; i >= 0; --i) {
      if (locals.get(i) != VerificationTypes.TOP) {
        if (locals.get(i).isTwoWord) {
          return i + 2;
        } else {
          return i + 1;
        }
      }
    }
    return 0;
  }

  public void emit(Attribute attr, ConstantPool cp) {
    if (prevFrame != null) {
      emitDiffFrame(attr, cp, prevFrame);
    } else {
      emitFullFrame(attr, cp);
    }
  }

  /**
   * Tries to generate a non-full stack frame.
   *
   * <p>A diff stack frame can often be generated based on the previous stack
   * frame.  This is not necessary but it may save space in the generated
   * bytecode.
   *
   * <p>If it is not possible to generate a diff stack frame, then a full stack
   * frame is output instead.
   */
  private void emitDiffFrame(Attribute attr, ConstantPool cp, StackFrame prev) {
    int assigned = assignedLocals();
    int prevAssigned = prev.assignedLocals();
    boolean sameLocals = assigned == prevAssigned && sameLocals(prev, assigned);

    if (top == 0 && sameLocals) {
      // Same locals and zero stack items.
      if (offset <= 63) {
        attr.u1(offset); // same_frame
      } else {
        attr.u1(251); // same_frame_extended
        attr.u2(offset); // offset_delta
      }
      return;
    }
    if (top == 1 && sameLocals) {
      // Same locals and one stack item frame.
      if (offset <= 63) {
        attr.u1(64 + offset); // same_locals_1_stack_item_frame
      } else {
        attr.u1(247); // same_locals_1_stack_item_fram_extended
        attr.u2(offset); // offset_delta
      }
      stack.get(0).emit(attr, cp);
      return;
    }
    if (top == 0 && sameLocals(prev, assigned) && assigned < prevAssigned) {
      // Chop frame.
      int diff = 0;
      for (int i = assigned; i < prevAssigned; ++i) {
        diff += 1;
        if (prev.getLocal(i).isTwoWord) {
          i += 1;
        }
      }
      if (diff <= 3) {
        attr.u1(251 - diff); // chop_frame
        attr.u2(offset); // offset_delta
        return;
      }
    }
    if (top == 0 && sameLocals(prev, prevAssigned) && assigned > prevAssigned) {
      // Append frame.
      int diff = 0;
      for (int i = prevAssigned; i < assigned; ++i) {
        diff += 1;
        if (getLocal(i).isTwoWord) {
          i += 1;
        }
      }
      if (diff <= 3) {
        attr.u1(251 + diff); // append_frame
        attr.u2(offset); // offset_delta
        for (int i = prevAssigned; i < assigned; ++i) {
          VerificationType type = locals.get(i);
          type.emit(attr, cp);
          if (type.isTwoWord) {
            i += 1;
          }
        }
        return;
      }
    }

    emitFullFrame(attr, cp);
  }

  /**
   * This generates a full stack frame describing the state of local variable types
   * and operand stack items at the current bytecode position.
   */
  private void emitFullFrame(Attribute attr, ConstantPool cp) {
    attr.u1(255); // full_frame
    attr.u2(offset); // offset_delta

    int assigned = assignedLocals();

    // Compute number of different locals (excluding duplicate entries).
    int numLocals = 0;
    for (int i = 0; i < assigned; ++i) {
      VerificationType type = locals.get(i);
      numLocals += 1;
      if (type.isTwoWord) {
        i += 1;
      }
    }
    attr.u2(numLocals); // number_of_locals
    for (int i = 0; i < assigned; ++i) {
      VerificationType type = locals.get(i);
      type.emit(attr, cp);
      if (type.isTwoWord) {
        i += 1;
      }
    }
    attr.u2(stack.size()); // number_of_stack_items
    for (VerificationType type : stack) {
      type.emit(attr, cp);
    }
  }

  /**
   * Combine two stack frames (control flow merges)
   */
  public boolean merge(StackFrame other) {
    boolean diff = false;
    int i = 0;
    for (; i < maxLocals && i < other.maxLocals; ++i) {
      VerificationType typeA = locals.get(i);
      VerificationType typeB = other.locals.get(i);
      if (!typeA.sameType(typeB)) {
        diff = true;
        locals.set(i, typeA.nca(typeB));
      }
    }
    for (; i < maxLocals || i < other.maxLocals; ++i) {
      if (i < maxLocals && getLocal(i) != VerificationTypes.TOP) {
        diff = true;
      }
      setLocal(i, VerificationTypes.TOP);
    }
    for (i = 0; i < top && i < other.top; ++i) {
      VerificationType typeA = stack.get(i);
      VerificationType typeB = other.stack.get(i);
      if (!typeA.sameType(typeB)) {
        diff = true;
        stack.set(i, typeA.nca(typeB));
      }
    }
    for (; i < top && i < other.top; ++i) {
      stack.set(i, VerificationTypes.TOP);
    }
    while (top > i) {
      stack.remove(--top);
    }
    return diff;
  }

  /**
   * Make this a copy of another stack frame
   */
  public void copy(StackFrame other) {
    locals.clear();
    locals.addAll(other.locals);
    maxLocals = other.maxLocals;

    stack.clear();
    stack.addAll(other.stack);
    maxStack = other.maxStack;
    stackSize = other.stackSize;
    top = other.top;
  }

  public boolean sameLocals(StackFrame other, int limit) {
    if (maxLocals < limit || other.maxLocals < limit) {
      return false;
    }
    for (int i = 0; i < limit; ++i) {
      VerificationType typeA = locals.get(i);
      VerificationType typeB = other.locals.get(i);
      if (!typeA.sameType(typeB)) {
        return false;
      }
    }
    return true;
  }

  public void clearStack() {
    stackSize = 0;
    top = 0;
    stack.clear();
  }

}
