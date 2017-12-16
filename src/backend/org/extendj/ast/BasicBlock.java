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
import java.util.BitSet;
import java.util.Collection;

/**
 * Logs local variable and stack type changes in a basic block.
 *
 * <p>This class is used for constructing stack map frames.
 */
class BasicBlock {
  private static final int PUSH = 0;
  private static final int POP = -1;
  private static final int DUP = -2;
  private static final int DUP_X1 = -3;
  private static final int DUP_X2 = -4;
  private static final int SWAP = -5;

  BasicBlock next = null; // Null if there is no fallthrough.
  int start, end; // Start/end PC.
  int label = -1;
  boolean reachable = false;
  boolean working = false;
  Collection<Integer> jumps = new ArrayList<Integer>(); // Jump targets.
  Collection<BasicBlock> succ = new ArrayList<BasicBlock>(); // Jump targets.
  // Exception handler targets:
  Collection<CodeGeneration.ExceptionEntry> excp = new ArrayList<CodeGeneration.ExceptionEntry>();
  int preds = 0; // Predecessors (incoming jumps).

  /**
   * Stack state at block entry.
   */
  StackFrame entryStack = null;

  private int[] diffs = new int[16];
  private VerificationType[] types = new VerificationType[16];
  int top = 0;
  private ArrayList<VerificationType> locals = new ArrayList<VerificationType>(8);
  public int maxLocals = 0;

  /** Tracks locals that change type in the logged block. */
  private BitSet change = new BitSet();

  /**
   * @param label the label for this block. Negative labels are used
   * for sequential numbering of basic blocks.
   */
  public BasicBlock(int label, int startPc) {
    this.label = label;
    start = startPc;
  }

  public void setNext(BasicBlock target) {
    this.next = target;
  }

  public void addJump(int label) {
    jumps.add(label);
  }

  public void connect(BasicBlock target) {
    succ.add(target);
    target.preds += 1;
  }

  public StackFrame exitStack() {
    return apply(entryStack);
  }

  @Override public String toString() {
    return String.format("%s (%d..%d)", name(), start, end);
  }

  public String name() {
    if (label < 0) {
      if (label == -1) {
        return "S";
      } else {
        return "B" + (-label - 1);
      }
    } else {
      return "L" + label;
    }
  }

  /**
   * Grow the memory diff log to ensure it fits a new diff entry.
   */
  private void grow() {
    if (top == diffs.length) {
      int[] newDiff = new int[diffs.length * 2];
      VerificationType[] newType = new VerificationType[diffs.length * 2];
      System.arraycopy(diffs, 0, newDiff, 0, top);
      System.arraycopy(types, 0, newType, 0, top);
      diffs = newDiff;
      types = newType;
    }
  }

  public void push(VerificationType type) {
    grow();
    diffs[top] = PUSH;
    types[top++] = type;
  }

  public void pop() {
    if (top >= 1 && diffs[top - 1] == PUSH) {
      diffs[top - 1] = types[top - 1].variableSize();
      mergeDiffs();
      return;
    }
    if (top >= 2 && diffs[top - 1] > 0 && diffs[top - 2] == PUSH) {
      // Remove the pop, and add the variable size to the diff.
      int diff = diffs[top - 1];
      diffs[top - 2] = diff + types[top - 2].variableSize();
      top -= 1;
      mergeDiffs();
      return;
    }
    grow();
    diffs[top++] = POP;
  }

  /**
   * Collapses adjacent stack differences (removes the smaller diff).
   */
  private void mergeDiffs() {
    if (top >= 2
        && diffs[top - 1] > 0
        && diffs[top - 2] > 0) {
      int diff1 = diffs[top - 1];
      int diff2 = diffs[top - 2];
      if (diff1 > diff2) {
        diffs[top - 2] = diff1;
      }
      top -= 1;
    }
  }

  public void pop(int num) {
    for (int i = 0; i < num; ++i) {
      pop();
    }
  }

  public void allocate(int index, VerificationType type) {
    setLocal(index, type);
  }

  public void store(int index, VerificationType type) {
    pop();
    setLocal(index, type);
    if (type.isTwoWord) {
      setLocal(index + 1, VerificationTypes.TOP);
    }
  }

  public void setLocal(int index, VerificationType type) {
    while (index > maxLocals) {
      locals.add(VerificationTypes.TOP);
      maxLocals += 1;
    }
    if (index < maxLocals) {
      VerificationType prevType = locals.get(index);
      if (type == VerificationTypes.TOP
          || (prevType != VerificationTypes.TOP
              && !prevType.sameType(type))) {
        change.set(index);
      }
      locals.set(index, type);
    } else {
      locals.add(type);
      maxLocals += 1;
    }
  }

  public void dup() {
    grow();
    if (top >= 1 && diffs[top - 1] == PUSH) {
      diffs[top] = PUSH;
      types[top] = types[top - 1];
      top += 1;
      return;
    }
    if (top >= 2
        && diffs[top - 1] > 0
        && diffs[top - 2] == PUSH) {
      diffs[top] = PUSH;
      types[top] = types[top - 2];
      top += 1;
      return;
    }
    diffs[top++] = DUP;
  }

  public void dup_x1() {
    grow();
    diffs[top++] = DUP_X1;
  }

  public void dup_x2() {
    grow();
    diffs[top++] = DUP_X2;
  }

  public void swap() {
    grow();
    diffs[top++] = SWAP;
  }

  public StackFrame apply(StackFrame in) {
    StackFrame out = new StackFrame(in);
    for (int i = 0; i < top; ++i) {
      switch (diffs[i]) {
        case PUSH:
          out.push(types[i]);
          break;
        case POP:
          out.pop();
          break;
        case DUP:
          out.dup();
          break;
        case DUP_X1:
          out.dup_x1();
          break;
        case DUP_X2:
          out.dup_x2();
          break;
        case SWAP:
          out.swap();
          break;
        default:
          if (diffs[i] <= 0) {
            throw new Error("Illegal temporary stack size diff!");
          }
          out.stackDiff(diffs[i]);
      }
    }
    for (int i = 0; i < maxLocals; ++i) {
      VerificationType nextType = locals.get(i);
      if (nextType != VerificationTypes.TOP) {
        out.setLocal(i, nextType);
        if (nextType.isTwoWord) {
          out.setLocal(i + 1, VerificationTypes.TOP);
        }
      }
    }
    return out;
  }

  public void printOps() {
    for (int i = 0; i < top; ++i) {
      if (i > 0) {
        System.out.print(", ");
      }
      switch (diffs[i]) {
        case PUSH:
          System.out.print("PUSH " + types[i]);
          break;
        case POP:
          System.out.print("POP");
          break;
        case DUP:
          System.out.print("DUP");
          break;
        case DUP_X1:
          System.out.print("DUP_X1");
          break;
        case DUP_X2:
          System.out.print("DUP_X2");
          break;
        case SWAP:
          System.out.print("SWAP");
          break;
        default:
          System.out.print("DIFF +" + diffs[i]);
      }
    }
    System.out.println();
  }

  /**
   * Computes the intersection of assigned types during a full basic block
   * for each local variable given the initial state in the argument frame.
   */
  public void localSubset(StackFrame frame) {
    for (int i = 0; i < maxLocals; ++i) {
      if (change.get(i)) {
        frame.setLocal(i, VerificationTypes.TOP);
      } else {
        VerificationType nextType = locals.get(i);
        VerificationType prevType = i < frame.maxLocals()
            ? frame.getLocal(i)
            : VerificationTypes.TOP;
        if (nextType != VerificationTypes.TOP
            && prevType != VerificationTypes.TOP
            && !nextType.sameType(prevType)) {
          frame.setLocal(i, VerificationTypes.TOP);
        }
      }
    }
  }
}
