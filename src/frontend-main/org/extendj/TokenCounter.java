/* Copyright (c) 2013-2018, Jesper Öqvist <jesper.oqvist@cs.lth.se>
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
package org.extendj;

import beaver.Symbol;

import org.extendj.parser.JavaParser.Terminals;
import org.extendj.scanner.JavaScanner;
import org.extendj.scanner.Unicode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * Counts Java tokens in the files listed on the command line.  Excludes
 * whitespace, comments, curly braces, and parenthesis. The input files need
 * not be pure Java files. Each unexpected character increases the token count
 * by one.
 * @author Jesper Öqvist <jesper.oqvist@cs.lth.se>
 */
public class TokenCounter {
  private int allTokens = 0;
  private int allLines = 0;
  private int allImports = 0;

  /**
   * Count tokens in some Java source files.
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.err.println("No arguments given!");
      printHelp();
      System.exit(1);
    }
    List<String> filteredArgs = new ArrayList<String>();
    boolean csvOption = false;
    for (String arg: args) {
      if (arg.equals("-h")) {
        printHelp();
        System.exit(0);
      }
      if (arg.equals("--csv")) {
        csvOption = true;
      } else {
        filteredArgs.add(arg);
      }
    }
    TokenCounter counter = new TokenCounter(csvOption);
    counter.processArgs(filteredArgs);
  }

  private final boolean csv;

  public TokenCounter(boolean csv) {
    this.csv = csv;
  }

  public static void printHelp() {
    System.out.println("Usage: TokenCounter [OPTIONS] <Java files> [@filelist]");
    System.out.println();
    System.out.println("OPTIONS:");
    System.out.println("  --csv    CSV output with one line per input file.");
    System.out.println();
    System.out.println("Counts Java tokens in the files listed on the command line.");
    System.out.println("Excludes whitespace, comments, curly braces, and parenthesis.");
    System.out.println("The input files need not be pure Java files. Each unexpected");
    System.out.println("character increases the total token count by one.");
  }

  public void processArgs(List<String> args) {
    if (csv) {
      System.out.println("file,tokens,lines,imports");
    }
    for (String arg : args) {
      if (arg.startsWith("@")) {
        processFileList(arg.substring(1));
      } else {
        processFile(arg);
      }
    }
    if (!csv) {
      System.out.println("tokens: " + allTokens);
      System.out.println("lines: " + allLines);
      System.out.println("imports: " + allImports);
    }
  }

  /**
   * @return number of tokens in the files listed in the file list
   */
  public void processFileList(String filename) {
    try {
      Scanner scanner = new Scanner(new File(filename));
      while (scanner.hasNextLine()) {
        processFile(scanner.nextLine());
      }
      scanner.close();
    } catch (IOException e) {
      System.err.println("Could not read file list: " + filename);
    }
  }

  /**
   * @return number of tokens in the file
   */
  public void processFile(String filename) {
    int tokens = 0, lines = 0, imports = 0;
    File file = new File(filename);
    if (!file.isFile()) {
      System.err.println("Warning: could not open file " + filename);
    } else {
      try {
        FileInputStream is = new FileInputStream(file);
        JavaScanner scanner = new JavaScanner(new Unicode(is));
        boolean inImport = false;
        int prevLine = 0;
        while (true) {
          try {
            Symbol next = scanner.nextToken();
            int id = next.getId();
            if (id == Terminals.EOF) {
              break;
            }
            switch (id) {
              case Terminals.IMPORT:
                inImport = true;
                imports += 1;
                break;
              case Terminals.SEMICOLON:
                inImport = false;
                break;
              case Terminals.DOCUMENTATION_COMMENT:
              case Terminals.LPAREN:
              case Terminals.RPAREN:
              case Terminals.LBRACE:
              case Terminals.RBRACE:
                // Not counted!
                break;
              default:
                if (!inImport) {
                  // Only count non-import tokens.
                  tokens += 1;
                  int currentLine = beaver.Symbol.getLine(next.getStart());
                  if (currentLine != prevLine) {
                    lines += 1;
                    prevLine = currentLine;
                  }
                }
            }
          } catch (beaver.Scanner.Exception e) {
            System.err.format("Warning %s:%d:%d: %s%n", filename, e.line, e.column, e.getMessage());
            tokens += 1;
          }
        }
        is.close();
        if (csv) {
          System.out.format("%s,%d,%d,%d%n", filename, tokens, lines, imports);
          allTokens += tokens;
          allLines += lines;
          allImports += imports;
        }
      } catch (IOException e) {
        System.err.println("Warning: could not count tokens of " + filename);
        System.err.println(e.getMessage());
      }
    }
  }
}
