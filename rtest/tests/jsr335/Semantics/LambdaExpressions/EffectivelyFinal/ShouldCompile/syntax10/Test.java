// .result=COMPILE_PASS

public class Test {
	public interface TestInterface {
		public int functMethod(); 
	}
	
	public static void main(String[] args) {
		int[] a = new int[10];
		a[0] = 3;
		a[0] = 4;
		a[1] = 1;
		a[1] = 4;
		TestInterface t = () -> a[0] + a[1];
		a[0] = 0;
    }
}