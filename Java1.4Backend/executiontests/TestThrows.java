class TestThrows {
  static void t() throws NullPointerException {
    throw new NullPointerException();
  }
  public static void main(String[] args) {
    try {
      t();
    } 
    catch(NullPointerException e) {
      System.out.println("Exception caught");
    }
  }
}
