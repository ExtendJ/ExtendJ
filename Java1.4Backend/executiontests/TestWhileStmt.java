public class TestWhileStmt {
    public static void main(String[] args) {
      int z = 0;
      while(z != 5) {
	z = z + 1;
	if(z == 3)
		continue;
      	System.out.println(z);
      }  	
    }
}
