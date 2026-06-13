// .result=COMPILE_PASS


public class Test {		
	public static void main(String[] args) {
		int a = 5;
		class InnerClass {
			int b;
			{
				b = a;
			}
		}

    }

}