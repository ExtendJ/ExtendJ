// Reachable statement.
// .result=COMPILE_PASS
class Test {

  class ResourceInitializationException extends Exception {}
  class NonInitializableResource implements AutoCloseable {
    public NonInitializableResource() throws ResourceInitializationException {
      throw new ResourceInitializationException();
    }
    public void close() {}
  }

  public void pass() {
    try {
      try (NonInitializableResource r = new NonInitializableResource()) {
      }
    } catch (ResourceInitializationException e) {
      return;
    }
  }

}
