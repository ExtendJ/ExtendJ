// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * We shouldn't get any NullPointerExceptions here.
 */
public class Test {
	static class BenignResource implements AutoCloseable {
		public boolean isClosed = false;
		public void close() {
			isClosed = true;
		}
	}

	public static void main(String[] args) {
		try {
			try (BenignResource r = null) {
			}
		} catch (NullPointerException npe) {
			fail("NPE should not be thrown for null resource");
		}
	}
}
