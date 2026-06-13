// javac version 1.8.0_05 fails this test because of a bug:
// http://bugs.java.com/bugdatabase/view_bug.do?bug_id=8037947
// .result=COMPILE_PASS
import java.util.*;

class Test {
  interface X<A> { <T extends List<Map<?, A>>> void execute(int a); }
  interface Y<B> { <S extends List<Map<?, B>>> void execute(int a); }

  @FunctionalInterface
  interface Exec extends Y, X { }
}
