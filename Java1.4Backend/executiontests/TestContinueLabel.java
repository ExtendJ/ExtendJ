public class TestContinueLabel {
	public static void main(String[] args) {
	int j = 0;
        l: while(j < 2) {
	   j = j + 1;
	   int i = 0;
	   while(i < 10) {
	     i = i + 1;
	     System.out.print(j);
	     System.out.print(" ");
	     System.out.println(i);
	     if(i < 3)
	       continue l;
           }
        }
	}
}
