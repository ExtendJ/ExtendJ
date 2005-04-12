/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 1998-2001  Gerwin Klein <lsf@jflex.de>                    *
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

/* Java 1.2 language lexer specification */

/* Use together with unicode.flex for Unicode preprocesssing */
/* and java12.grammar a Java 1.2 parser                      */

/* Note that this lexer specification is not tuned for speed.
   It is in fact quite slow on integer and floating point literals.
   For a production quality application (e.g. a Java compiler)
   this could be optimized */

/* 2003-12: Modified to work with Page parser generator */

/* 2004-07: ENUM, ASSERT keyword, ELLIPSIS token added to support
            java 1.5. / TR, MP
 */

package parser;

import beaver.Symbol;
import beaver.Scanner;
import parser.JavaParser.Terminals;

%%

%public
%final
%class JavaScanner
%extends Scanner
%unicode
%function nextToken
%type Symbol
%yylexthrow Scanner.Exception
%eofval{
	return newSymbol(Terminals.EOF, "end-of-file");
%eofval}
%line
%column

%{
  StringBuffer string = new StringBuffer(128);

  private Symbol newSymbol(short id)
  {
    return new Symbol(id, yyline + 1, yycolumn + 1, yylength(), yytext());
  }

  private Symbol newSymbol(short id, Object value)
  {
    return new Symbol(id, yyline + 1, yycolumn + 1, yylength(), value);
  }

%}


/* main character classes */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} |
          {DocumentationComment}

TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/*" "*"+ [^/*] ~"*/"

/* identifiers */
Identifier = [:jletter:][:jletterdigit:]*

/* integer literals */
DecIntegerLiteral = 0 | [1-9][0-9]*
DecLongLiteral    = {DecIntegerLiteral} [lL]

HexIntegerLiteral = 0 [xX] 0* {HexDigit} {1,8}
HexLongLiteral    = 0 [xX] 0* {HexDigit} {1,16} [lL]
HexDigit          = [0-9a-fA-F]

OctIntegerLiteral = 0+ [1-3]? {OctDigit} {1,15}
OctLongLiteral    = 0+ 1? {OctDigit} {1,21} [lL]
OctDigit          = [0-7]

/* floating point literals */
FloatLiteral  = ({FLit1}|{FLit2}|{FLit3}) {Exponent}? [fF]
DoubleLiteral = ({FLit1}|{FLit2}|{FLit3}) {Exponent}?

FLit1    = [0-9]+ \. [0-9]*
FLit2    = \. [0-9]+
FLit3    = [0-9]+
Exponent = [eE] [+-]? [0-9]+

/* string and character literals */
StringCharacter = [^\r\n\"\\]
SingleCharacter = [^\r\n\'\\]

%state STRING, CHARLITERAL

%%

<YYINITIAL> {

  /* keywords */
  "assert"                       { return newSymbol(Terminals.ASSERT); }
  "abstract"                     { return newSymbol(Terminals.ABSTRACT); }
  "boolean"                      { return newSymbol(Terminals.BOOLEAN); }
  "break"                        { return newSymbol(Terminals.BREAK); }
  "byte"                         { return newSymbol(Terminals.BYTE); }
  "case"                         { return newSymbol(Terminals.CASE); }
  "catch"                        { return newSymbol(Terminals.CATCH); }
  "char"                         { return newSymbol(Terminals.CHAR); }
  "class"                        { return newSymbol(Terminals.CLASS); }
/*  "const"                        { return newSymbol(Terminals.CONST); }*/
  "continue"                     { return newSymbol(Terminals.CONTINUE); }
  "do"                           { return newSymbol(Terminals.DO); }
  "double"                       { return newSymbol(Terminals.DOUBLE); }
  "else"                         { return newSymbol(Terminals.ELSE); }
/*  "enum"                         { return newSymbol(Terminals.ENUM); }*/
  "extends"                      { return newSymbol(Terminals.EXTENDS); }
  "final"                        { return newSymbol(Terminals.FINAL); }
  "finally"                      { return newSymbol(Terminals.FINALLY); }
  "float"                        { return newSymbol(Terminals.FLOAT); }
  "for"                          { return newSymbol(Terminals.FOR); }
  "default"                      { return newSymbol(Terminals.DEFAULT); }
  "implements"                   { return newSymbol(Terminals.IMPLEMENTS); }
  "import"                       { return newSymbol(Terminals.IMPORT); }
  "instanceof"                   { return newSymbol(Terminals.INSTANCEOF); }
  "int"                          { return newSymbol(Terminals.INT); }
  "interface"                    { return newSymbol(Terminals.INTERFACE); }
  "long"                         { return newSymbol(Terminals.LONG); }
  "native"                       { return newSymbol(Terminals.NATIVE); }
  "new"                          { return newSymbol(Terminals.NEW); }
/*  "goto"                         { return newSymbol(Terminals.GOTO); }*/
  "if"                           { return newSymbol(Terminals.IF); }
  "public"                       { return newSymbol(Terminals.PUBLIC); }
  "short"                        { return newSymbol(Terminals.SHORT); }
  "super"                        { return newSymbol(Terminals.SUPER); }
  "switch"                       { return newSymbol(Terminals.SWITCH); }
  "synchronized"                 { return newSymbol(Terminals.SYNCHRONIZED); }
  "package"                      { return newSymbol(Terminals.PACKAGE); }
  "private"                      { return newSymbol(Terminals.PRIVATE); }
  "protected"                    { return newSymbol(Terminals.PROTECTED); }
  "transient"                    { return newSymbol(Terminals.TRANSIENT); }
  "return"                       { return newSymbol(Terminals.RETURN); }
  "void"                         { return newSymbol(Terminals.VOID); }
  "static"                       { return newSymbol(Terminals.STATIC); }
  "while"                        { return newSymbol(Terminals.WHILE); }
  "this"                         { return newSymbol(Terminals.THIS); }
  "throw"                        { return newSymbol(Terminals.THROW); }
  "throws"                       { return newSymbol(Terminals.THROWS); }
  "try"                          { return newSymbol(Terminals.TRY); }
  "volatile"                     { return newSymbol(Terminals.VOLATILE); }
  "strictfp"                     { return newSymbol(Terminals.STRICTFP); }

  /* boolean literals */
  "true"                         { return newSymbol(Terminals.BOOLEAN_LITERAL, "true"); }
  "false"                        { return newSymbol(Terminals.BOOLEAN_LITERAL, "false"); }

  /* null literal */
  "null"                         { return newSymbol(Terminals.NULL_LITERAL); }


  /* separators */
  "("                            { return newSymbol(Terminals.LPAREN); }
  ")"                            { return newSymbol(Terminals.RPAREN); }
  "{"                            { return newSymbol(Terminals.LBRACE); }
  "}"                            { return newSymbol(Terminals.RBRACE); }
  "["                            { return newSymbol(Terminals.LBRACK); }
  "]"                            { return newSymbol(Terminals.RBRACK); }
  ";"                            { return newSymbol(Terminals.SEMICOLON); }
  ","                            { return newSymbol(Terminals.COMMA); }
  "."                            { return newSymbol(Terminals.DOT); }

  /* operators */
  "="                            { return newSymbol(Terminals.EQ); }
  ">"                            { return newSymbol(Terminals.GT); }
  "<"                            { return newSymbol(Terminals.LT); }
  "!"                            { return newSymbol(Terminals.NOT); }
  "~"                            { return newSymbol(Terminals.COMP); }
  "?"                            { return newSymbol(Terminals.QUESTION); }
  ":"                            { return newSymbol(Terminals.COLON); }
  "=="                           { return newSymbol(Terminals.EQEQ); }
  "<="                           { return newSymbol(Terminals.LTEQ); }
  ">="                           { return newSymbol(Terminals.GTEQ); }
  "!="                           { return newSymbol(Terminals.NOTEQ); }
  "&&"                           { return newSymbol(Terminals.ANDAND); }
  "||"                           { return newSymbol(Terminals.OROR); }
  "++"                           { return newSymbol(Terminals.PLUSPLUS); }
  "--"                           { return newSymbol(Terminals.MINUSMINUS); }
  "+"                            { return newSymbol(Terminals.PLUS); }
  "-"                            { return newSymbol(Terminals.MINUS); }
  "*"                            { return newSymbol(Terminals.MULT); }
  "/"                            { return newSymbol(Terminals.DIV); }
  "&"                            { return newSymbol(Terminals.AND); }
  "|"                            { return newSymbol(Terminals.OR); }
  "^"                            { return newSymbol(Terminals.XOR); }
  "%"                            { return newSymbol(Terminals.MOD); }
  "<<"                           { return newSymbol(Terminals.LSHIFT); }
  ">>"                           { return newSymbol(Terminals.RSHIFT); }
  ">>>"                          { return newSymbol(Terminals.URSHIFT); }
  "+="                           { return newSymbol(Terminals.PLUSEQ); }
  "-="                           { return newSymbol(Terminals.MINUSEQ); }
  "*="                           { return newSymbol(Terminals.MULTEQ); }
  "/="                           { return newSymbol(Terminals.DIVEQ); }
  "&="                           { return newSymbol(Terminals.ANDEQ); }
  "|="                           { return newSymbol(Terminals.OREQ); }
  "^="                           { return newSymbol(Terminals.XOREQ); }
  "%="                           { return newSymbol(Terminals.MODEQ); }
  "<<="                          { return newSymbol(Terminals.LSHIFTEQ); }
  ">>="                          { return newSymbol(Terminals.RSHIFTEQ); }
  ">>>="                         { return newSymbol(Terminals.URSHIFTEQ); }
/*  "..."                          { return newSymbol(Terminals.ELLIPSIS); }*/

  /* string literal */
  \"                             { yybegin(STRING); string.setLength(0); }

  /* character literal */
  \'                             { yybegin(CHARLITERAL); }

  /* numeric literals */

  {DecIntegerLiteral}            { return newSymbol(Terminals.INTEGER_LITERAL, yytext()); }
  {DecLongLiteral}               { return newSymbol(Terminals.LONG_LITERAL, yytext().substring(0,yylength()-1)); }

  {HexIntegerLiteral}            { return newSymbol(Terminals.INTEGER_LITERAL, yytext()); }
  {HexLongLiteral}               { return newSymbol(Terminals.LONG_LITERAL, yytext().substring(0, yylength()-1)); }

  {OctIntegerLiteral}            { return newSymbol(Terminals.INTEGER_LITERAL, yytext()); }
  {OctLongLiteral}               { return newSymbol(Terminals.LONG_LITERAL, yytext().substring(0, yylength()-1)); }

  {FloatLiteral}                 { return newSymbol(Terminals.FLOATING_POINT_LITERAL, yytext().substring(0,yylength()-1)); }
  {DoubleLiteral}                { return newSymbol(Terminals.DOUBLE_LITERAL, yytext()); }
  {DoubleLiteral}[dD]            { return newSymbol(Terminals.DOUBLE_LITERAL, yytext().substring(0,yylength()-1)); }
  
  /* comments */
  {Comment}                      { /* ignore */ }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }

  /* identifiers */
  {Identifier}                   { return newSymbol(Terminals.IDENTIFIER, yytext()); }
}

<STRING> {
  \"                             { yybegin(YYINITIAL); return newSymbol(Terminals.STRING_LITERAL, string.toString()); }

  {StringCharacter}+             { string.append( yytext() ); }

  /* escape sequences */
  "\\b"                          { string.append( '\b' ); }
  "\\t"                          { string.append( '\t' ); }
  "\\n"                          { string.append( '\n' ); }
  "\\f"                          { string.append( '\f' ); }
  "\\r"                          { string.append( '\r' ); }
  "\\\""                         { string.append( '\"' ); }
  "\\'"                          { string.append( '\'' ); }
  "\\\\"                         { string.append( '\\' ); }
  \\[0-3]?{OctDigit}?{OctDigit}  { char val = (char) Integer.parseInt(yytext().substring(1),8); string.append( val ); }

  /* error cases */
  \\.                            { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
  {LineTerminator}               { throw new RuntimeException("Unterminated string at end of line"); }
}

<CHARLITERAL> {
  {SingleCharacter}\'            { yybegin(YYINITIAL); return newSymbol(Terminals.CHARACTER_LITERAL, new Character(yytext().charAt(0))); }

  /* escape sequences */
  "\\b"\'                        { yybegin(YYINITIAL); return newSymbol(Terminals.CHARACTER_LITERAL, new Character('\b'));}
  "\\t"\'                        { yybegin(YYINITIAL); return newSymbol(Terminals.CHARACTER_LITERAL, new Character('\t'));}
  "\\n"\'                        { yybegin(YYINITIAL); return newSymbol(Terminals.CHARACTER_LITERAL, new Character('\n'));}
  "\\f"\'                        { yybegin(YYINITIAL); return newSymbol(Terminals.CHARACTER_LITERAL, new Character('\f'));}
  "\\r"\'                        { yybegin(YYINITIAL); return newSymbol(Terminals.CHARACTER_LITERAL, new Character('\r'));}
  "\\\""\'                       { yybegin(YYINITIAL); return newSymbol(Terminals.CHARACTER_LITERAL, new Character('\"'));}
  "\\'"\'                        { yybegin(YYINITIAL); return newSymbol(Terminals.CHARACTER_LITERAL, new Character('\''));}
  "\\\\"\'                       { yybegin(YYINITIAL); return newSymbol(Terminals.CHARACTER_LITERAL, new Character('\\')); }
  \\[0-3]?{OctDigit}?{OctDigit}\' { yybegin(YYINITIAL);
			                              int val = Integer.parseInt(yytext().substring(1,yylength()-1),8);
			                            return newSymbol(Terminals.CHARACTER_LITERAL, new Character((char)val)); }

  /* error cases */
  \\.                            { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
  {LineTerminator}               { throw new RuntimeException("Unterminated character literal at end of line"); }
}

/* error fallback */
.|\n                             { throw new RuntimeException("Illegal character \""+yytext()+ "\" at line "+yyline+", column "+yycolumn); }
<<EOF>>                          { return newSymbol(Terminals.EOF); }
