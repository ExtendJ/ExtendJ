/* Copyright (c) 2005-2008, Torbjorn Ekman
 *                    2013, Jesper Öqvist <jesper.oqvist@cs.lth.se>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Lund University nor the names of its
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package AST;

/**
 * Utility class for bytecode generation.
 */
class ByteArray {
	private int stackDepth = 0;
	private int maxStackDepth = 0;
	private int size = 64;
	private byte[] bytes = new byte[size];
	private int pos = 0;
	private int lastGotoPos = 0;

	/**
	 * Grow the byte array to fit more bytes if needed.
	 * @param num number of bytes to grow
	 */
	void grow(int num) {
		int newsize = pos + num;
		if(newsize-1 >= size) {
			newsize = Math.max(newsize, size * 2);
			byte[] ba = new byte[newsize];
			System.arraycopy(bytes, 0, ba, 0, size);
			size = newsize;
			bytes = ba;
		}
	}

	ByteArray add(int i) {
		return add((byte) i);
	}

	ByteArray add(byte b) {
		grow(1);
		bytes[pos++] = b;
		return this;
	}

	/**
	 * Skip forward a number of bytes.
	 */
	void skip(int num) {
		grow(num);
		pos += num;
	}

	ByteArray add4(int i) {
		grow(4);
		bytes[pos++] = (byte) (i >> 24 & 0xFF);
		bytes[pos++] = (byte) (i >> 16 & 0xFF);
		bytes[pos++] = (byte) (i >> 8 & 0xFF);
		bytes[pos++] = (byte) (i & 0xFF);
		return this;
	}

	ByteArray add2(int index) {
		grow(2);
		bytes[pos++] = (byte) (index >> 8 & 0xff);
		bytes[pos++] = (byte) (index & 0xff);
		return this;
	}

	ByteArray emit(byte b) {
		changeStackDepth(BytecodeDebug.stackChange(b));
		add(b);
		return this;
	}

	ByteArray emitGoto(byte b) {
		changeStackDepth(BytecodeDebug.stackChange(b));
		lastGotoPos = pos;
		add(b);
		return this;
	}

	ByteArray emit(byte b, int stackChange) {
		changeStackDepth(stackChange);
		add(b);
		return this;
	}

	public int maxStackDepth() {
		return maxStackDepth;
	}

	public int stackDepth() {
		return stackDepth;
	}

	public void changeStackDepth(int i) {
		stackDepth += i;
		if(stackDepth > maxStackDepth)
			maxStackDepth = stackDepth;
	}

	public int pos() { return pos; }

	public int lastGotoPos() { return lastGotoPos; }

	public void setPos(int index) {
		pos = index;
		grow(0);
	}

	public int size() { return pos; }

	public byte get(int index) throws IndexOutOfBoundsException {
		return bytes[index];
	}

	public void set(int index, byte value) throws IndexOutOfBoundsException {
		bytes[index] = value;
	}

	public String toString() {
		StringBuilder b = new StringBuilder();
		for(int i = 0; i < pos; i++) b.append(" " + bytes[i]);
		return b.toString();
	}

	public byte[] toArray() {
		byte[] b = new byte[pos];
		System.arraycopy(bytes, 0, b, 0, pos);
		return b;
	}
}

