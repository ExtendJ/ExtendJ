/* Copyright (c) 2005-2008, Torbjorn Ekman
 * Copyright (c) 2019, Jesper Öqvist
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
package org.extendj.scanner;

import java.io.FilterReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Translates Unicode escape sequences to Unicode characters.
 */
@SuppressWarnings("javadoc")
public class UnicodeEscapeReader extends Reader {
  private static final int SIZE = 1024;
  private final char[] buffer = new char[SIZE];
  private final Reader in;
  private int pos = 0;
  private int length = 0;
  private int numConsecutiveBackSlash = 0;

  public UnicodeEscapeReader(Reader in) throws IOException {
    this.in = in;
  }

  public UnicodeEscapeReader(InputStream stream) throws IOException {
    this(new InputStreamReader(stream, "UTF8"));
  }

  /**
   * Refill the internal buffer from the filtered stream.
   *
   * <p>Assumes that the underlying stream reads at least one character
   * if the end of the stream has not been reached.
   */
  private void refill() throws IOException {
    if (pos >= length) {
      pos = 0;
      int size = in.read(buffer, 0, SIZE);
      length = size != -1 ? size : 0;
    }
  }

  private int next() throws IOException {
    refill();
    return pos < length ? buffer[pos++] : -1;
  }

  private int peek() throws IOException {
    refill();
    return pos < length ? buffer[pos] : -1;
  }

  /** Read next character, translating Unicode escapes. */
  @Override
  public int read() throws IOException {
    int current = next();
    if (current != '\\') {
      numConsecutiveBackSlash = 0;
      return current;
    }
    numConsecutiveBackSlash ^= 1;
    if (numConsecutiveBackSlash == 1 && peek() == 'u') {
      // UnicodeEscape found.
      // Skip 'u' prefix.
      do {
        next();
      } while (peek() == 'u');
      // The next four characters must be hexadecimal digits or else a
      // compile-time error is thrown.
      int result = 0;
      for (int i = 0; i < 4; i++) {
        int c = next();
        int value = Character.digit((char) c, 16);
        if (value == -1) {
          throw new Error("Invalid Unicode Escape");
        }
        result <<= 4;
        result += value;
      }
      numConsecutiveBackSlash = 0;
      return result;
    } else {
      return '\\';
    }
  }

  @Override
  public int read(char cbuf[], int off, int len) throws IOException {
    int i = off;
    while (i < off + len) {
      int c = read();
      if (c < 0) {
        break;
      }
      cbuf[i++] = (char) c;
    }
    return (i > off) ? i - off : -1;
  }

  @Override
  public boolean ready() throws IOException {
    return pos < length || super.ready();
  }

  @Override
  public void close() throws IOException {
    in.close();
  }
}
