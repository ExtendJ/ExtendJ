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

import beaver.core.Token;
import beaver.core.Scanner;

%%

%public
%final
%class JavaScanner
%extends Scanner
%unicode
%function nextToken
%type Token
%yylexthrow Scanner.Exception
%eofval{
	return Token.New(JavaParser.Tokens.EOF, "end-of-file");
%eofval}
%line
%column

%{
  StringBuffer string = new StringBuffer(128);

  private Token token(short id)
  {
    return Token.New(id, yyline + 1, yycolumn + 1);
  }

  private Token token(short id, Object value)
  {
    return Token.New(id, yyline + 1, yycolumn + 1, value);
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
  "assert"                       { return token(JavaParser.Tokens.ASSERT); }
  "abstract"                     { return token(JavaParser.Tokens.ABSTRACT); }
  "boolean"                      { return token(JavaParser.Tokens.BOOLEAN); }
  "break"                        { return token(JavaParser.Tokens.BREAK); }
  "byte"                         { return token(JavaParser.Tokens.BYTE); }
  "case"                         { return token(JavaParser.Tokens.CASE); }
  "catch"                        { return token(JavaParser.Tokens.CATCH); }
  "char"                         { return token(JavaParser.Tokens.CHAR); }
  "class"                        { return token(JavaParser.Tokens.CLASS); }
  "const"                        { return token(JavaParser.Tokens.CONST); }
  "continue"                     { return token(JavaParser.Tokens.CONTINUE); }
  "do"                           { return token(JavaParser.Tokens.DO); }
  "double"                       { return token(JavaParser.Tokens.DOUBLE); }
  "else"                         { return token(JavaParser.Tokens.ELSE); }
/*  "enum"                         { return token(JavaParser.Tokens.ENUM); }*/
  "extends"                      { return token(JavaParser.Tokens.EXTENDS); }
  "final"                        { return token(JavaParser.Tokens.FINAL); }
  "finally"                      { return token(JavaParser.Tokens.FINALLY); }
  "float"                        { return token(JavaParser.Tokens.FLOAT); }
  "for"                          { return token(JavaParser.Tokens.FOR); }
  "default"                      { return token(JavaParser.Tokens.DEFAULT); }
  "implements"                   { return token(JavaParser.Tokens.IMPLEMENTS); }
  "import"                       { return token(JavaParser.Tokens.IMPORT); }
  "instanceof"                   { return token(JavaParser.Tokens.INSTANCEOF); }
  "int"                          { return token(JavaParser.Tokens.INT); }
  "interface"                    { return token(JavaParser.Tokens.INTERFACE); }
  "long"                         { return token(JavaParser.Tokens.LONG); }
  "native"                       { return token(JavaParser.Tokens.NATIVE); }
  "new"                          { return token(JavaParser.Tokens.NEW); }
  "goto"                         { return token(JavaParser.Tokens.GOTO); }
  "if"                           { return token(JavaParser.Tokens.IF); }
  "public"                       { return token(JavaParser.Tokens.PUBLIC); }
  "short"                        { return token(JavaParser.Tokens.SHORT); }
  "super"                        { return token(JavaParser.Tokens.SUPER); }
  "switch"                       { return token(JavaParser.Tokens.SWITCH); }
  "synchronized"                 { return token(JavaParser.Tokens.SYNCHRONIZED); }
  "package"                      { return token(JavaParser.Tokens.PACKAGE); }
  "private"                      { return token(JavaParser.Tokens.PRIVATE); }
  "protected"                    { return token(JavaParser.Tokens.PROTECTED); }
  "transient"                    { return token(JavaParser.Tokens.TRANSIENT); }
  "return"                       { return token(JavaParser.Tokens.RETURN); }
  "void"                         { return token(JavaParser.Tokens.VOID); }
  "static"                       { return token(JavaParser.Tokens.STATIC); }
  "while"                        { return token(JavaParser.Tokens.WHILE); }
  "this"                         { return token(JavaParser.Tokens.THIS); }
  "throw"                        { return token(JavaParser.Tokens.THROW); }
  "throws"                       { return token(JavaParser.Tokens.THROWS); }
  "try"                          { return token(JavaParser.Tokens.TRY); }
  "volatile"                     { return token(JavaParser.Tokens.VOLATILE); }
  "strictfp"                     { return token(JavaParser.Tokens.STRICTFP); }

  /* boolean literals */
  "true"                         { return token(JavaParser.Tokens.BOOLEAN_LITERAL, "true"); }
  "false"                        { return token(JavaParser.Tokens.BOOLEAN_LITERAL, "false"); }

  /* null literal */
  "null"                         { return token(JavaParser.Tokens.NULL_LITERAL); }


  /* separators */
  "("                            { return token(JavaParser.Tokens.LPAREN); }
  ")"                            { return token(JavaParser.Tokens.RPAREN); }
  "{"                            { return token(JavaParser.Tokens.LBRACE); }
  "}"                            { return token(JavaParser.Tokens.RBRACE); }
  "["                            { return token(JavaParser.Tokens.LBRACK); }
  "]"                            { return token(JavaParser.Tokens.RBRACK); }
  ";"                            { return token(JavaParser.Tokens.SEMICOLON); }
  ","                            { return token(JavaParser.Tokens.COMMA); }
  "."                            { return token(JavaParser.Tokens.DOT); }

  /* operators */
  "="                            { return token(JavaParser.Tokens.EQ); }
  ">"                            { return token(JavaParser.Tokens.GT); }
  "<"                            { return token(JavaParser.Tokens.LT); }
  "!"                            { return token(JavaParser.Tokens.NOT); }
  "~"                            { return token(JavaParser.Tokens.COMP); }
  "?"                            { return token(JavaParser.Tokens.QUESTION); }
  ":"                            { return token(JavaParser.Tokens.COLON); }
  "=="                           { return token(JavaParser.Tokens.EQEQ); }
  "<="                           { return token(JavaParser.Tokens.LTEQ); }
  ">="                           { return token(JavaParser.Tokens.GTEQ); }
  "!="                           { return token(JavaParser.Tokens.NOTEQ); }
  "&&"                           { return token(JavaParser.Tokens.ANDAND); }
  "||"                           { return token(JavaParser.Tokens.OROR); }
  "++"                           { return token(JavaParser.Tokens.PLUSPLUS); }
  "--"                           { return token(JavaParser.Tokens.MINUSMINUS); }
  "+"                            { return token(JavaParser.Tokens.PLUS); }
  "-"                            { return token(JavaParser.Tokens.MINUS); }
  "*"                            { return token(JavaParser.Tokens.MULT); }
  "/"                            { return token(JavaParser.Tokens.DIV); }
  "&"                            { return token(JavaParser.Tokens.AND); }
  "|"                            { return token(JavaParser.Tokens.OR); }
  "^"                            { return token(JavaParser.Tokens.XOR); }
  "%"                            { return token(JavaParser.Tokens.MOD); }
  "<<"                           { return token(JavaParser.Tokens.LSHIFT); }
  ">>"                           { return token(JavaParser.Tokens.RSHIFT); }
  ">>>"                          { return token(JavaParser.Tokens.URSHIFT); }
  "+="                           { return token(JavaParser.Tokens.PLUSEQ); }
  "-="                           { return token(JavaParser.Tokens.MINUSEQ); }
  "*="                           { return token(JavaParser.Tokens.MULTEQ); }
  "/="                           { return token(JavaParser.Tokens.DIVEQ); }
  "&="                           { return token(JavaParser.Tokens.ANDEQ); }
  "|="                           { return token(JavaParser.Tokens.OREQ); }
  "^="                           { return token(JavaParser.Tokens.XOREQ); }
  "%="                           { return token(JavaParser.Tokens.MODEQ); }
  "<<="                          { return token(JavaParser.Tokens.LSHIFTEQ); }
  ">>="                          { return token(JavaParser.Tokens.RSHIFTEQ); }
  ">>>="                         { return token(JavaParser.Tokens.URSHIFTEQ); }
  "..."                          { return token(JavaParser.Tokens.ELLIPSIS); }

  /* string literal */
  \"                             { yybegin(STRING); string.setLength(0); }

  /* character literal */
  \'                             { yybegin(CHARLITERAL); }

  /* numeric literals */

  {DecIntegerLiteral}            { return token(JavaParser.Tokens.INTEGER_LITERAL, yytext()); }
  {DecLongLiteral}               { return token(JavaParser.Tokens.LONG_LITERAL, yytext().substring(0,yylength()-1)); }

  {HexIntegerLiteral}            { return token(JavaParser.Tokens.INTEGER_LITERAL, yytext()); }
  {HexLongLiteral}               { return token(JavaParser.Tokens.LONG_LITERAL, yytext().substring(0, yylength()-1)); }

  {OctIntegerLiteral}            { return token(JavaParser.Tokens.INTEGER_LITERAL, yytext()); }
  {OctLongLiteral}               { return token(JavaParser.Tokens.LONG_LITERAL, yytext().substring(0, yylength()-1)); }

  {FloatLiteral}                 { return token(JavaParser.Tokens.FLOATING_POINT_LITERAL, yytext().substring(0,yylength()-1)); }
  {DoubleLiteral}                { return token(JavaParser.Tokens.DOUBLE_LITERAL, yytext()); }
  {DoubleLiteral}[dD]            { return token(JavaParser.Tokens.DOUBLE_LITERAL, yytext().substring(0,yylength()-1)); }
  
  /* comments */
  {Comment}                      { /* ignore */ }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }

  /* identifiers */
  {Identifier}                   { return token(JavaParser.Tokens.IDENTIFIER, yytext()); }
}

<STRING> {
  \"                             { yybegin(YYINITIAL); return token(JavaParser.Tokens.STRING_LITERAL, string.toString()); }

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
  {SingleCharacter}\'            { yybegin(YYINITIAL); return token(JavaParser.Tokens.CHARACTER_LITERAL, new Character(yytext().charAt(0))); }

  /* escape sequences */
  "\\b"\'                        { yybegin(YYINITIAL); return token(JavaParser.Tokens.CHARACTER_LITERAL, new Character('\b'));}
  "\\t"\'                        { yybegin(YYINITIAL); return token(JavaParser.Tokens.CHARACTER_LITERAL, new Character('\t'));}
  "\\n"\'                        { yybegin(YYINITIAL); return token(JavaParser.Tokens.CHARACTER_LITERAL, new Character('\n'));}
  "\\f"\'                        { yybegin(YYINITIAL); return token(JavaParser.Tokens.CHARACTER_LITERAL, new Character('\f'));}
  "\\r"\'                        { yybegin(YYINITIAL); return token(JavaParser.Tokens.CHARACTER_LITERAL, new Character('\r'));}
  "\\\""\'                       { yybegin(YYINITIAL); return token(JavaParser.Tokens.CHARACTER_LITERAL, new Character('\"'));}
  "\\'"\'                        { yybegin(YYINITIAL); return token(JavaParser.Tokens.CHARACTER_LITERAL, new Character('\''));}
  "\\\\"\'                       { yybegin(YYINITIAL); return token(JavaParser.Tokens.CHARACTER_LITERAL, new Character('\\')); }
  \\[0-3]?{OctDigit}?{OctDigit}\' { yybegin(YYINITIAL);
			                              int val = Integer.parseInt(yytext().substring(1,yylength()-1),8);
			                            return token(JavaParser.Tokens.CHARACTER_LITERAL, new Character((char)val)); }

  /* error cases */
  \\.                            { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
  {LineTerminator}               { throw new RuntimeException("Unterminated character literal at end of line"); }
}

/* error fallback */
.|\n                             { throw new RuntimeException("Illegal character \""+yytext()+ "\" at line "+yyline+", column "+yycolumn); }
<<EOF>>                          { return token(JavaParser.Tokens.EOF); }
