package runtime;

import java.util.ArrayList;

@SuppressWarnings("javadoc")
public class Test {
  public static void testNull(Object o) {
    testTrue(o == null);
  }

  public static void testNotNull(Object o) {
    testTrue(o != null);
  }

  /**
   * Tests that two references are identical
   * @param expected
   * @param actual
   */
  public static void testSame(Object expected, Object actual) {
    testSame(null, expected, actual);
  }

  public static void testSame(String message, Object expected, Object actual) {
    if (expected != actual) {
      String m = message != null ? message + " " : "";
      m += String.format("Expected same: <%s> and <%s>", expected, actual);
      error(m);
    }
  }

  public static void testNotSame(Object unexpected, Object actual) {
    testNotSame(null, unexpected, actual);
  }

  public static void testNotSame(String message, Object unexpected, Object actual) {
    if (unexpected == actual) {
      String m = message != null ? message + " " : "";
      m += String.format("Expected not same: <%s>", actual);
      error(m);
    }
  }

  public static void testEqual(Object expected, Object actual) {
    testEquals(null, expected, actual);
  }

  public static void testEquals(Object expected, Object actual) {
    testEquals(null, expected, actual);
  }

  public static void testEqual(String message, Object expected, Object actual) {
    testEquals(message, expected, actual);
  }

  public static void testEquals(String message, Object expected, Object actual) {
    String m = message != null ? message + " " : "";
    if (expected == null || actual == null) {
      if (expected != actual) {
        error(m + "expected: <" + expected + ">, was: <" + actual + ">");
      }
    } else {
      if (!expected.equals(actual)) {
        error(m + "expected: <" + expected + ">, was: <" + actual + ">");
      }
    }

  }

  public static void test(String message, boolean condition) {
    testTrue(message, condition);
  }

  public static void test(boolean condition) {
    testTrue(condition);
  }

  public static void testTrue(String message, boolean condition) {
    testEqual(message, true, condition);
  }

  public static void testTrue(boolean condition) {
    testTrue(null, condition);
  }

  public static void testFalse(String message, boolean condition) {
    testEqual(message, false, condition);
  }

  public static void testFalse(boolean condition) {
    testFalse(null, condition);
  }

  public static void fail(String message) {
    error(message);
  }

  public static void error(String message) {
    System.err.print("Test failed. ");
    if (message != null) {
      System.err.print(message);
    }
    System.err.println();

    ArrayList<String> ignore = new ArrayList<String>();
    ignore.add(Test.class.getName());
    ignore.add("java.lang.Thread");
    for (StackTraceElement e : Thread.currentThread().getStackTrace()) {
      if (!ignore.contains(e.getClassName())) {
        System.err.println("- " + e);
      }
    }
  }
}

