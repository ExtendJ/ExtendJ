
public class Test {
	public static void main(String[] args) {
		Object f = x -> { if(x > 4) 
		{ return 5; } 
		   else 
		   	{ return y -> y + 1; }
		 }; 
    }
}
