// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public void functMethod(int a); 
	}
	
	public static void main(String[] args) {
		TestInterface t = (int a) -> {
			int i = 0;
			while(i < a) {
				if(i == 10)
					continue;
				System.out.println("Not 10");
				i++;
			}
		};
    }
}