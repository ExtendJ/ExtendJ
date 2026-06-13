package runtime;

// This class is used to test that the synthetic modifier bytecode flag is
// respected for the $assertionsDisabled field.
public class AssertionsDisabled {
  private void foo() {
    // This assertion causes the $assertionsDisabled field to be generated:
    assert System.getProperty("bork") == null;
  }
}
