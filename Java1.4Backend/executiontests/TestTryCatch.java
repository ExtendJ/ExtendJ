public class TestTryCatch {
	public static void main(String[] args) {
		try {
  		  throw new Error("Error thrown");
		} catch (Error e) {
		  System.out.println("Error caught");
		}
	}
}
