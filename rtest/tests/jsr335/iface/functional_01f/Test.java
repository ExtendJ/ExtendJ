// Type arguments are invariant, so G<W> is not a subtype of G<V> even
// though W extends V. Neither inherited execute(int) is then
// return-type-substitutable for the other, hence FavUpD_IjVY has no function
// descriptor and is not a functional interface.
// .result=COMPILE_FAIL
class Test {
  interface G<T> {}

  interface X<V, W extends V> {
    G<W> cows(int a);
  }

  interface Y<V, W extends V> {
    G<V> cows(int a);
  }

  @FunctionalInterface
  interface FavUpD_IjVY<V, W extends V> extends X<V, W>, Y<V, W> { }
}
