public class Generics10 {
  public static void main(String[] args) {
    Seq<String> strs = new Seq<String>("a", new Seq<String>("b", new Seq<String>()));
    Seq<Number> nums = new Seq<Number>(new Integer(1), new Seq<Number>(new Double(1.5), new Seq<Number>()));
    Seq<String>.Zipper<Number> zipper = null;
    zipper.zip(nums);
  }
}
class Pair<E1, E2> {
}

class Seq<E> {
  E head;
  Seq<E> tail;
  Seq() {
    this(null, null);
  }
  boolean isEmpty() {
    return tail == null;
  }
  Seq(E head, Seq<E> tail) {
    this.head = head;
    this.tail = tail;
  }
  class Zipper<T> {
    Seq<Pair<E, T>> zip(Seq<T> that) {
      return new Seq<Pair<E,T>>();
    }
  }
}
/*
class SeqString {
  String head;
  SeqString tail;
  SeqString();
  boolean isEmpty();
  SeqString(String head, SeqString tail)
  class Zipper<T> {
    Seq<Pair<SeqString, T>> zip(Seq<T> that);
  }
}
*/
  
