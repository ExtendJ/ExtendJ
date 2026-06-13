// Resource initialization exception must be caught or declared to be thrown.
// .result=COMPILE_FAIL
class Test {

  class ResourceInitializationException extends Exception {}
  class NonInitializableResource implements AutoCloseable {
    public NonInitializableResource() throws ResourceInitializationException {
      throw new ResourceInitializationException();
    }
    public void close() {}
  }

  public void fail() {
    try (NonInitializableResource r = new NonInitializableResource()) {
    }
  }

}
