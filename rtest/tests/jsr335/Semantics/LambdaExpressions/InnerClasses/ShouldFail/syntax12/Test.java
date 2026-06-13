// .result=COMPILE_FAIL


public class Test {		
	public static int foo(int a) {
		return a + 2;
	}
	
	public static void main(String[] args) {
		int a;
		int b;
		int[][][][][] arr = new int[10][10][10][10][10];
		int[] arr2 = new int[10];
		int c = arr[0][1 + foo(a = 2)][1][arr2[b = 1]][arr2[a = 0]];

		Runnable r = new Runnable() {
			public void run() { 
				int d = a + b + c;
			}
		};

    }

}