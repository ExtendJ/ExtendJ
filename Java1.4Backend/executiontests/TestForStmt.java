public class TestForStmt {
    public static void main(String[] args) {
	int i;
        for (i=0; i<10; i= i+1) {
            i = i;
	    if(i == 4)
		    continue;
            System.out.println(i);
        }
    }
}
