// .result=EXEC_PASS
public class Test {
	public static void main(String[] args) {
		while (true) {
			Object o = new Object();
			synchronized (o) {
				break;
			}
		}
	}
}
