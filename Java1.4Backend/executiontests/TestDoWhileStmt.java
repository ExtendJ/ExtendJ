public class TestDoWhileStmt {
    public static void main(String[] args) {
	int i = 0;
	do {
	  i = i + 1;
	  if(i == 3)
		  continue;
	  System.out.println(i);
	} while( i < 5);
    }
}
