class T4_C<V> {
    private void m(T4_C<? extends Integer> i) { }
    class Inner { void f() { T4_C<Integer> c = null; c.m(null); } }
}
