public class Generics11 {
  public static void main(String[] args) {
    Collection<? extends Number> c = null;
    Number n = c.first();
    c.add(null);
    Iterator<? extends Number> i = c.iterator();
    Number o = c.iterator().next();
    //Collection<? extends Integer> i = null;
    //n = i;
    /*
    Collection<Object> c1 = null;
    printCollection(c1);
    Collection<String> c2 = null;
    printCollection(c2);
    */
  }
  
  /*
  public static void printCollection(Collection<? extends Object> c) {
    for(Iterator<Object> iter = c.iterator(); iter.hasNext(); ) {
      Object o = iter.next();
    }
  }
  */
  /*
  public static void printCollection(Collection<? extends Object> c) {
    for(Iterator<Object> iter = c.iterator(); iter.hasNext(); ) {
      Object o = iter.next();
    }
    c.add(null);
  }
  */
}
interface Iterable<T> {
    Iterator<T> iterator();
}
interface Iterator<E> {
    boolean hasNext();
    E next();
    void remove();
}

interface Collection<E> extends Iterable<E> {
    E first();
    Iterator<E> iterator();
    boolean add(E o);
    /*
    boolean containsAll(Collection<?> c);
    boolean addAll(Collection<? extends E> c);
    */
}
