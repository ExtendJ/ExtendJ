class TestInstanceof {
  public static void main(String[] args) {
    String s = "hej";
    System.out.println(s instanceof String);
    System.out.println(null instanceof String);
    System.out.println(new Object() instanceof TestInstanceof);

  }
}

