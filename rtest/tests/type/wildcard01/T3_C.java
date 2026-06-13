interface I<U> { }
class T3_C<V> implements I<V> {
    private void m(I<? extends V> i) {
		T3_C<V> c = null;
		c.m(null);
	}
}
