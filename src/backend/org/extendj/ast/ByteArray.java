/* Copyright (c) 2005-2008, Torbjorn Ekman
 *               2013-2017, Jesper Öqvist <jesper.oqvist@cs.lth.se>
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

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Utility class for building a byte array for bytecode generation.
 */
class ByteArray {
  private byte[] bytes = new byte[64];
  private int pos = 0;

  /**
   * Grow the byte array to fit more bytes if needed.
   * @param num number of bytes to grow
   */
  void grow(int num) {
    int size = bytes.length;
    int newsize = pos + num;
    if (newsize > size) {
      newsize = Math.max(newsize, size * 2);
      byte[] newBytes = new byte[newsize];
      System.arraycopy(bytes, 0, newBytes, 0, size);
      bytes = newBytes;
    }
  }

  void add(int i) {
    add((byte) i);
  }

  void add(byte b) {
    grow(1);
    bytes[pos++] = b;
  }

  /**
   * Skip forward a number of bytes.
   */
  void skip(int num) {
    grow(num);
    pos += num;
  }

  void add4(int i) {
    grow(4);
    bytes[pos++] = (byte) (i >> 24 & 0xFF);
    bytes[pos++] = (byte) (i >> 16 & 0xFF);
    bytes[pos++] = (byte) (i >> 8 & 0xFF);
    bytes[pos++] = (byte) (i & 0xFF);
  }

  void add2(int index) {
    grow(2);
    bytes[pos++] = (byte) (index >> 8 & 0xff);
    bytes[pos++] = (byte) (index & 0xff);
  }

  public int pos() {
    return pos;
  }

  public void setPos(int index) {
    pos = index;
    grow(0);
  }

  public int size() {
    return pos;
  }

  public byte get(int index) throws IndexOutOfBoundsException {
    return bytes[index];
  }

  public void set(int index, byte value) throws IndexOutOfBoundsException {
    bytes[index] = value;
  }

  @Override
  public String toString() {
    return String.format("byte[%d]", size());
  }

  public byte[] toArray() {
    byte[] b = new byte[pos];
    System.arraycopy(bytes, 0, b, 0, pos);
    return b;
  }

  public void write(DataOutputStream out) throws IOException {
    out.write(bytes, 0, pos);
  }

}

