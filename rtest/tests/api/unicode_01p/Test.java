// This tests the behaviour of the isPrimitive attribute.
// Boxed primitive types are treated as primitive types.
// This should probably be changed eventually.
// https://bitbucket.org/extendj/extendj/issues/309/java-8-exception-handling-with-inferred
// .result=EXEC_PASS
// .classpath=@EXTENDJ_LIB@
import beaver.Symbol;
import org.extendj.parser.JavaParser;
import org.extendj.scanner.JavaScanner;
import org.extendj.scanner.UnicodeEscapeReader;

import java.io.StringReader;

public class Test {
  public static void main(String[] args) throws Exception {
    printSymbols("\":o\"");
    printSymbols("\"😯\"");
    printSymbols("\"\uD83D\uDE2F\"");
    printSymbols("\"\\uD83D\\uDE2F\"");
	}

	static void printSymbols(String text) throws Exception {
    JavaScanner scanner = new JavaScanner(new UnicodeEscapeReader(new StringReader(text)));
    while (true) {
      Symbol sym = scanner.nextToken();
      if (sym.getId() == JavaParser.Terminals.EOF) {
        break;
      }
      System.out.printf("Symbol: '%s'%n", sym.value);
    }
	}
}

