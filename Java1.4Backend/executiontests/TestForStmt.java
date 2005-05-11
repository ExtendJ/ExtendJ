public class TestForStmt {
  public static void main(String[] args) {
    for (int i=0; i<10; i= i+1) {
      i = i;
      Object j = "";
      if(i == 4)
        continue;
      System.out.println(i);
    }
  }
}
