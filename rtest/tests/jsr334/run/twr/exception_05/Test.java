// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * This test tests part of the new suppressed exceptions semantics.
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
			int i = 1;
			if (i == 1) throw new Exception();
		} catch (Exception e) {
			testEqual("suppressed exceptions",
					1, e.getSuppressed().length);
			return;
		}
		fail("Exception was expected");
	}
}
