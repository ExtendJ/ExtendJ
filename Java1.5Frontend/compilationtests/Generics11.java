public class Generics11 {
  public static void main(String[] args) {
    /*
    Collection<Object> c1 = null;
    printCollection(c1);
    Collection<String> c2 = null;
    printCollection(c2);
    */
  }
  public static void printCollection(Collection<? extends Object> c) {
    for(Iterator<Object> iter = c.iterator(); iter.hasNext(); ) {
      Object o = iter.next();
    }
  }
}
public interface Iterable<T> {
    Iterator<T> iterator();
}

public interface Iterator<E> {
    boolean hasNext();
    E next();
    void remove();
}

public interface Collection<E> extends Iterable<E> {
    Iterator<E> iterator();
    boolean add(E o);
    /*
    boolean containsAll(Collection<?> c);
    boolean addAll(Collection<? extends E> c);
    */
}
