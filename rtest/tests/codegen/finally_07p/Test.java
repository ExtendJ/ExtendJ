// Tests try-statement bytecode generation
// .result=EXEC_PASS
public class Test {

  public static void main(String argv[]) {
    int x = 0;
    int y = 0;
    int i;
    for ( i = 0; i < 2; i++ ) {
      try {
        try {
          if ( i == 0 ) {
            continue;
          }
        } finally {
          x += 1;
        }
      } finally {
        y += x*3;
      }
    }

    if (y != 9) {
      System.out.println("FAIL");
      System.out.println("expected y = 9 but was " + y);
      System.exit(1);
    }
  }
}

