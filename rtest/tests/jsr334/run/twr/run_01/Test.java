// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

public class Test {
	static class BenignResource implements AutoCloseable {
		public boolean isClosed = false;
		public void close() {
			isClosed = true;
		}
	}

	public static void main(String[] args) {
		BenignResource myResource = new BenignResource();
		try (BenignResource r = myResource) {
			int i = 2;
			testEqual(r.isClosed, false);
		}
		testEqual(myResource.isClosed, true);
	}
}
