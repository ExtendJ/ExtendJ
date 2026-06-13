// .result=COMPILE_FAIL
public class Test {			
	public static void main(String[] args) {
		int a = 10;
		class InnerClass {
			static {
		        a = 5;
		    }
		}

    }

}