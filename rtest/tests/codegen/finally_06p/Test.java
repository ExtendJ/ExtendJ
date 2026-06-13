// All finally blocks are reached even if one of them does not complete normally.
// .result=EXEC_PASS
public class Test {
 
  public static void main(String args[]) {
    int i = 0;
L1: {
      try {
        try {
        } finally {
          break L1;
        }
      } finally {
        i++;
      }
    }
     
    if (i != 1) {
      System.out.println("FAIL");
      System.out.println("expected i=1, but was " + i);
      System.exit(1);
    }
  }
 
}
