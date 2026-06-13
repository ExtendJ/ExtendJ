// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public String functMethod(); 
	}
	
	public static void main(String[] args) {
		TestInterface t = () -> { 
			Runnable r = new Runnable() {
				public void run() { return; }
			};
			return "Hej";
		};
    }
}