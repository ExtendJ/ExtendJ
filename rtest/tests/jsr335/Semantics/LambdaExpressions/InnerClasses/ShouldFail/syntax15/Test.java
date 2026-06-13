// .result=COMPILE_FAIL
public class Test {			
	public static void main(String[] args) {
		int a = args[0].length();
		
		class InnerClass {
			void method() {
				a = 0;
			}
		}

    }

}