import java.util.*;
public class EnhancedFor {
  public static void main(String[] args) {
    List list = new ArrayList();
    list.add("First");
    list.add("Second");
    list.add("Third");
    for(Object o : list) {
      System.out.println(o);
    }
    
    String[] strings = new String[] { "Fourth", "Fifth" };
    for(String s : strings) {
      System.out.println(s);
    }
  }
}


