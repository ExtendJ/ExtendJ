public class TestBreakLabel {
	public static void main(String[] args) {
        l: {
	   int i = 0;
	   while(i < 10) {
	     System.out.println("Before");
	     if(i < 3)
	       break l;
           }
	   System.out.println("After");
        }
	}
}
