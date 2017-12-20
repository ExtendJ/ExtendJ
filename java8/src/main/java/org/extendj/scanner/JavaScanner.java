/* Copyright (c) 2014, Erik Hogeman <Erik.Hogemn@gmail.com>
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
package org.extendj.scanner;

import java.io.IOException;
import java.util.LinkedList;

import org.extendj.parser.JavaParser.Terminals;
import org.extendj.scanner.OriginalScanner;

import beaver.Scanner;
import beaver.Symbol;

/**
 * This is file is an implementation of a scanner wrapper used to parse Java 8.
 * The wrapper acts as a link between the parser and the original scanner, performing
 * token lookaheads in order to deduce what token to send to the parser.
 */
public class JavaScanner extends Scanner{
  private OriginalScanner scanner;
  private LinkedList<Symbol> tokenBuffer = new LinkedList<Symbol>();
  private boolean foundLparenConstruct = false;
  private Symbol currentSymbol = null;
  private Symbol lastSymbol = null;

  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param in  the java.io.Reader to read input from.
   */
  public JavaScanner(java.io.Reader in) {
    scanner = new OriginalScanner(in);
  }

  /* More illegal terminals can be added anytime.
   * Not covering all of them will not give any erronous behavior,
   * but make the parsing less efficient.
   *
   */
  private boolean isLegalGenerics(Symbol symbol) {
    switch (symbol.getId()) {
      case Terminals.LPAREN: return false;
      case Terminals.RPAREN: return false;
      case Terminals.SEMICOLON: return false;
      case Terminals.AND: return false;
      case Terminals.OR: return false;
      case Terminals.EQ: return false;
      case Terminals.LSHIFT: return false;
      case Terminals.LBRACE: return false;
      case Terminals.RBRACE: return false;
      case Terminals.EOF: return false;
      default: return true;
    }
  }

  /**
   * Checks that the symbol s, which has been found right before a '(',
   * is in cast context, in other words that a cast can be expected.
   * For example, if the token before was 'if', then the parenthesis
   * following is connected to the if-statement and can't be a cast.
   * @param s
   * @return true if s is in cast context, otherwise false
   */
  private boolean castContext(Symbol s) {
    if(s == null) {
      return false;
    }
    switch(s.getId()) {
      case Terminals.IF : return false;
      case Terminals.WHILE : return false;
      case Terminals.SWITCH : return false;
      case Terminals.FOR : return false;
      default : return true;
    }
  }

  /**
   * Peeks three tokens ahead to spot an inferred lambda expression
   * using a single parameter variable
   * @return true if an inferred lambda expression was found, otherwise false
   * @throws IOException
   * @throws Exception
   */
  private boolean isInferredLambda() throws IOException, Exception {
    Symbol firstLookahead;
    Symbol secondLookahead;
    Symbol thirdLookahead;
    if(tokenBuffer.size() >= 3) {
      firstLookahead = tokenBuffer.peek();
      secondLookahead = tokenBuffer.get(1);
      thirdLookahead = tokenBuffer.get(2);
    }
    else if(tokenBuffer.size() == 2) {
      firstLookahead = tokenBuffer.peek();
      secondLookahead = tokenBuffer.get(1);
      thirdLookahead = scanner.nextToken();
      tokenBuffer.addLast(thirdLookahead);
    }
    else if(tokenBuffer.size() == 1) {
      firstLookahead = tokenBuffer.peek();
      secondLookahead = scanner.nextToken();
      thirdLookahead = scanner.nextToken();
      tokenBuffer.addLast(secondLookahead);
      tokenBuffer.addLast(thirdLookahead);
    }
    else {
      firstLookahead = scanner.nextToken();
      tokenBuffer.addLast(firstLookahead);
      if(firstLookahead.getId() == Terminals.EOF) {
        return false;
      }
      secondLookahead = scanner.nextToken();
      tokenBuffer.addLast(secondLookahead);
      if(secondLookahead.getId() == Terminals.EOF) {
        return false;
      }
      thirdLookahead = scanner.nextToken();
      tokenBuffer.addLast(thirdLookahead);
    }

    if(firstLookahead.getId() == Terminals.IDENTIFIER &&
        secondLookahead.getId() == Terminals.RPAREN &&
        thirdLookahead.getId() == Terminals.RARROW) {
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * Peeks at the two next tokens in the stream and decides whether or
   * not those tokens make up the start of an and-list or not
   * (a list of the type: (a & b & c ...)
   * @return true if currently parsing an and-list, else false.
   */
  private boolean isAndList() throws IOException, Exception {
    Symbol firstLookahead;
    Symbol secondLookahead;
    if(tokenBuffer.size() >= 2) {
      firstLookahead = tokenBuffer.peek();
      secondLookahead = tokenBuffer.get(1);
    }
    else if(tokenBuffer.size() == 1) {
      firstLookahead = tokenBuffer.peek();
      secondLookahead = scanner.nextToken();
      tokenBuffer.addLast(secondLookahead);
    }
    else {
      firstLookahead = scanner.nextToken();
      tokenBuffer.addLast(firstLookahead);
      if(firstLookahead.getId() == Terminals.EOF) {
        return false;
      }
      secondLookahead = scanner.nextToken();
      tokenBuffer.addLast(secondLookahead);
    }

    if(firstLookahead.getId() == Terminals.IDENTIFIER &&
        secondLookahead.getId() == Terminals.AND) {
      return true;
    }
    else {
      return false;
    }

  }

  /**
   * @param s
   * @return true if s is a legal terminal as the starting
   * terminal in a unary_not_plus_minus production, otherwise false
   */
  private boolean firstInUnary(Symbol s) {
    switch(s.getId()) {
      case Terminals.IDENTIFIER : return true;
      case Terminals.NEW : return true;
      case Terminals.NUMERIC_LITERAL : return true;
      case Terminals.BOOLEAN_LITERAL : return true;
      case Terminals.CHARACTER_LITERAL : return true;
      case Terminals.STRING_LITERAL : return true;
      case Terminals.NULL_LITERAL : return true;
      case Terminals.BYTE : return true;
      case Terminals.SHORT : return true;
      case Terminals.INT : return true;
      case Terminals.LONG : return true;
      case Terminals.CHAR : return true;
      case Terminals.FLOAT : return true;
      case Terminals.DOUBLE : return true;
      case Terminals.BOOLEAN : return true;
      case Terminals.VOID : return true;
      case Terminals.THIS : return true;
      case Terminals.LPAREN : return true;
      case Terminals.SUPER : return true;
      case Terminals.COMP : return true;
      case Terminals.NOT : return true;
      default : return false;
    }
  }

  /**
   * When a start of an and-list has been found, this method is
   * used to further check if that is an intersection type cast,
   * which is done by looking at the first token after the
   * enclosing right paranthesis has been found. If that token
   * is a valid start token in a unary_not_plus_minus expression
   * then this returns true, otherwise false.
   * @return true if currently parsing an intersection cast, otherwise false
   */
  private boolean isIntersectionCast() throws IOException, Exception {
    boolean foundRparen = false;
    for(Symbol s : tokenBuffer) {
      if(foundRparen) {
        return firstInUnary(s);
      }

      //If nested parantheses are found, return false
      else if(s.getId() == Terminals.LPAREN) {
        return false;
      }

      else if(s.getId() == Terminals.EOF) {
        return false;
      }
      else if(s.getId() == Terminals.RPAREN) {
        foundRparen = true;
      }
    }

    if(foundRparen) {
      Symbol s = scanner.nextToken();
      tokenBuffer.addLast(s);
      return firstInUnary(s);
    }

    while(true) {
      Symbol s = scanner.nextToken();
      tokenBuffer.addLast(s);
      if(foundRparen) {
        return firstInUnary(s);
      }

      else if(s.getId() == Terminals.LPAREN) {
        return false;
      }

      else if(s.getId() == Terminals.EOF) {
        return false;
      }
      else if(s.getId() == Terminals.RPAREN) {
        foundRparen = true;
      }
    }
  }

  /**
   * This method checks if the last found LT token should be changed to a
   * TypeLT token. This method performs an arbitrary length of lookahead into
   * the token stream, and stores all of it in the token buffer.
   * @return true if the last LT token should be changed to LTTYPE
   * @throws IOException
   * @throws Exception
   */
  private boolean isTypeLT() throws IOException, Exception{
    int floatingLT = 1;
    if(!tokenBuffer.isEmpty()) {
      for(Symbol token : tokenBuffer) {
        if(floatingLT == 0) {
          if(token.getId() == Terminals.DOUBLECOLON) {
            return true;
          }
          else if(token.getId() != Terminals.LBRACK && token.getId() != Terminals.RBRACK) {
            return false;
          }
        }
        else if(floatingLT < 0) {
          return false;
        }
        else if(token.getId() == Terminals.LT) {
          floatingLT++;
        }
        else if(token.getId() == Terminals.GT) {
          floatingLT--;
        }
        else if(token.getId() == Terminals.RSHIFT) {
          floatingLT -= 2;
        }
        else if(token.getId() == Terminals.URSHIFT) {
          floatingLT -= 3;
        }
        else if(!isLegalGenerics(token)) {
          return false;
        }
      }
    }

    while(true) {
      Symbol token = null;
      token = scanner.nextToken();
      tokenBuffer.addLast(token);

      if(floatingLT == 0) {
        if(token.getId() == Terminals.DOUBLECOLON) {
          return true;
        }
        else if(token.getId() != Terminals.LBRACK && token.getId() != Terminals.RBRACK) {
          return false;
        }
      }
      else if(floatingLT < 0) {
        return false;
      }
      else if(token.getId() == Terminals.LT) {
        floatingLT++;
      }
      else if(token.getId() == Terminals.GT) {
        floatingLT--;
      }
      else if(token.getId() == Terminals.RSHIFT) {
        floatingLT -= 2;
      }
      else if(token.getId() == Terminals.URSHIFT) {
        floatingLT -= 3;
      }
      else if(!isLegalGenerics(token)) {
        return false;
      }
    }
  }

  @Override
  public Symbol nextToken() throws IOException, Exception {
    Symbol token = null;
    if(tokenBuffer.isEmpty()) {
      token = scanner.nextToken();
    }
    else {
      token = tokenBuffer.poll();
    }

    if(foundLparenConstruct) {
      foundLparenConstruct = false;
      return token;
    }

    lastSymbol = currentSymbol;
    currentSymbol = token;

    if(token.getId() == Terminals.LT) {
      if(isTypeLT()) {
        Symbol typeLTSymbol = new Symbol(Terminals.LTTYPE, token.getStart(), token.getEnd(), token.value);
        return typeLTSymbol;
      }
    }
    else if(token.getId() == Terminals.LPAREN) {
      if(isAndList() && castContext(lastSymbol)) {
        if(isIntersectionCast()) {
          tokenBuffer.addFirst(token);
          foundLparenConstruct = true;
          return new Symbol(Terminals.INTERCAST);
        }
      }
      else if(isInferredLambda()) {
        tokenBuffer.addFirst(token);
        foundLparenConstruct = true;
        return new Symbol(Terminals.INFERRED_LAMBDA);
      }
    }

    return token;
  }

}
