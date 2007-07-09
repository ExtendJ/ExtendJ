package scanner;

import beaver.Symbol;
import beaver.Scanner;
import parser.JavaParser.Terminals;
import AST.LexicalError;
import java.io.*;

%%

%public 
%final 
%class JavaScanner
%extends Scanner
%implements AST.PackageExtractor

%type Symbol 
%function nextToken 
%yylexthrow LexicalError

%unicode
%line %column

%{
  StringBuffer strbuf = new StringBuffer(128);
  int sub_line;
  int sub_column;

  private Symbol sym(short id) {
    return new Symbol(id, yyline + 1, yycolumn + 1, len(), str());
  }

  private Symbol sym(short id, String value) {
    return new Symbol(id, yyline + 1, yycolumn + 1, len(), value);
  }

  private String str() { return yytext(); }
  private int len() { return yylength(); }

  public JavaScanner() {
  }

  public String extractPackageName(String fileName) {
    StringBuffer packageName = new StringBuffer();
    try {
      Reader reader = new FileReader(fileName);
      JavaScanner scanner = new JavaScanner(new Unicode(reader));
      Symbol sym = scanner.nextToken();
      if(sym.getId() == Terminals.PACKAGE) {
        while(true) {
          sym = scanner.nextToken();
          if(sym.getId() == Terminals.SEMICOLON)
            break;
          packageName.append(sym.value);
        }
      }
      reader.close();
      if(packageName.length() != 0)
        packageName.append(".");
    } catch (java.lang.Exception e) {
    }
    return packageName.toString();
  }

%}


