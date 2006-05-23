/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 1998-2004 Gerwin Klein <lsf@jflex.de>                     *
 * All rights reserved.                                                    *
 *                                                                         *
 * This program is free software; you can redistribute it and/or modify    *
 * it under the terms of the GNU General Public License. See the file      *
 * COPYRIGHT for more information.                                         *
 *                                                                         *
 * This program is distributed in the hope that it will be useful,         *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of          *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the           *
 * GNU General Public License for more details.                            *
 *                                                                         *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA                 *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


/* §3.3 of the Java Language Specification :

UnicodeInputCharacter:

             UnicodeEscape

             RawInputCharacter

     UnicodeEscape:

             \ UnicodeMarker HexDigit HexDigit HexDigit HexDigit

     UnicodeMarker:

             u

             UnicodeMarker u

     RawInputCharacter:

             any Unicode character

     HexDigit: one of

             0 1 2 3 4 5 6 7 8 9 a b c d e f A B C D E F

only an even number of '\' is eligible to start a Unicode escape sequence

int numConsecutiveBackSlash = 0;

  char current = next();
  if(current == '\')
    numConsecutiveBackSlash++;
  else
    numConsecutiveBackSlash = 0;

  boolean isEven = numConsecutiveBackSlash & 0x01 == 0;
  if(isEven && lookahead() == 'u') {
    // UnicodeEscape found
    while(lookahead() == 'u')
      next();
    // The next four characters must be hexadecimal digits or else a compile-time error is thrown
    int result = 0;
    for(int i = 0; i < 4; i++) {
      char c = next();
      int value = Character.digit(c, 16);
      if(value == -1)
        throw new Error("Invalid Unicode Escape");
      result <<= 4;
      result += value;
    }
    return result;
  }
  return current;


*/

package parser;
import java.io.*;

%%

%public
%final
%class UnicodeEscapes
%extends FilterReader

%int
%function read

%switch
%16bit

UnicodeEscape   = {UnicodeMarker} {HexDigit} {4}
UnicodeMarker   = "u"+
HexDigit        = [0-9a-fA-F]

%state DIGITS

%init{
  super(in);
%init}

%{
  private boolean even;

  private int value() {
    int r = 0;

    for (int k = zzMarkedPos-4; k < zzMarkedPos; k++) {
      int c = zzBuffer[k];

      if (c >= 'a')
        c = 10 + c - 'a';
      else if (c >= 'A')
        c = 10 + c - 'A';
      else
        c = c - '0';

      r <<= 4;
      r += c;
    }

    return r;
  }

  public int read(char cbuf[], int off, int len) throws IOException {
    if ( !ready() ) return -1;

    len+= off;

    for (int i=off; i<len; i++) {
      int c = read();

      if (c < 0)
        return i-off;
      else
        cbuf[i] = (char) c;
    }

    return len-off;
  }

  public boolean markSupported() {
    return false;
  }

  public boolean ready() throws IOException {
    return !zzAtEOF && (zzCurrentPos < zzEndRead || zzReader.ready());
  }

%}

%%

<YYINITIAL> {
  \\               { even = false; return '\\'; }
  \\ / \\          { even = !even; return '\\'; }
  \\ / "u"         {
                     if (even) {
                       even = false;
                       return '\\';
                     }
                     else
                       yybegin(DIGITS);
                   }
  .|\n             { return zzBuffer[zzStartRead]; }

  <<EOF>>          { return -1; }
}

<DIGITS> {
  {UnicodeEscape}  { yybegin(YYINITIAL); return value();  }
  .|\n             { throw new Error("incorrect Unicode escape"); }

  <<EOF>>          { throw new Error("EOF in Unicode escape"); }
}
