// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * The NonCloseableResource will throw an exception when it is automatically
 * closed.
 */
public class Test {
	static class ResourceClosingException extends Exception {}

	static class NonCloseableResource implements AutoCloseable {
		public void close() throws ResourceClosingException {
			throw new ResourceClosingException();
		}
	}

	public static void main(String[] args) {
		try (NonCloseableResource r = new NonCloseableResource()) {
		} catch (ResourceClosingException e) {
			testEqual("suppressed exceptions",
					0, e.getSuppressed().length);
			return;
		}
		fail("Exception was expected");
	}
}
