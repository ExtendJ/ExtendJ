public class TestQuestionColon {
    public static void main(String[] args) {
      int i = 10;
      String s = i <= 10 ? "ok" : "NOT OK";
      System.out.println(s);
      s = i < 10 ? "NOT OK" : "ok";
      System.out.println(s);
    }
}
