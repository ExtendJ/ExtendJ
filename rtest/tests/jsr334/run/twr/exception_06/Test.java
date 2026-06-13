// .classpath: @RUNTIME_CLASSES@
import static runtime.Test.*;

public class Test {
	static class ResourceInitializationException extends Exception {}

	static class NonInitializableResource implements AutoCloseable {
		public NonInitializableResource() throws ResourceInitializationException {
			throw new ResourceInitializationException();
		}
		public void close() {}
	}

	public static void main(String[] args) {
		try {
			try (NonInitializableResource r = new NonInitializableResource()) {
			}
		} catch (ResourceInitializationException e) {
			return;
		}
		fail("Exception was expected");
	}
}
