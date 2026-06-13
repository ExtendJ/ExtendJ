// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

/**
 * If the initialization of a resource completes abruptly because
 * of a throw of value V1, and the automatic closing of on or more
 * resources that were already successfully initialized complete
 * abruptly because of throws of values V2..Vn, then the try-with-resources
 * statement will complete abruptly because of a throw of value V1
 * with V2..Vn added to the suppressed exceptions list.
 */
public class Test {
	static class ResourceInitializationException extends Exception {}
	static class ResourceClosingException extends Exception {}

	static class NonInitializableResource implements AutoCloseable {
		public NonInitializableResource() throws ResourceInitializationException {
			throw new ResourceInitializationException();
		}
		public void close() {}
	}

	static class NonCloseableResource implements AutoCloseable {
		public void close() throws ResourceClosingException {
			throw new ResourceClosingException();
		}
	}

	public static void main(String[] args) {
		try (NonCloseableResource r1 = new NonCloseableResource();
				NonInitializableResource r2 = new NonInitializableResource()) {
		} catch (ResourceClosingException e) {
			fail("Wrong exception kind!");
		} catch (ResourceInitializationException e) {
			testEqual("suppressed exception",
					1, e.getSuppressed().length);
			return;
		}
		fail("Exception was expected");
	}
}
