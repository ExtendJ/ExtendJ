// Test a simple builder pattern.
// https://bitbucket.org/extendj/extendj/issues/231/illegal-private-field-access-for-field-of
public class Test {
  public static final Test DEFAULT_ACTION =
      builder()
      .withAction(new Action() {
        public void doThing() {
          System.out.println("thing done");
        }
      })
    .build();

  private final Action action;

  Test(Builder builder) {
    this.action = builder.action;
  }

  private static Builder builder() {
    return new Builder();
  }

  private static class Builder {
    private Action action;

    private Builder withAction(Action action) {
      this.action = action;
      return this;
    }

    Test build() {
      return new Test(this);
    }
  }

  void run() {
    action.doThing();
  }

  public static void main(String[] args) {
    DEFAULT_ACTION.run();
  }
}

interface Action {
  void doThing();
}


