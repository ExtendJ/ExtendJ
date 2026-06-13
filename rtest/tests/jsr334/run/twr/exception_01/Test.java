// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

public class Test {
	static class ResourceClosingException extends Exception {}

	static class NonCloseableResource implements AutoCloseable {
		public void close() throws ResourceClosingException {
			throw new ResourceClosingException();
		}
	}

	public static void main(String[] args) {
		try (NonCloseableResource r = new NonCloseableResource()) {
		} catch (Exception e) {
			return;
		}
		fail("Exception was expected from close");
	}
}
