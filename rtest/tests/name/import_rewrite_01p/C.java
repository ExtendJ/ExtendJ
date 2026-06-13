import org.extendj.ast.Foo;

import java.util.concurrent.atomic.AtomicReference;

class C<T> {
  Foo.Constant constant = Foo.CONSTANT;
  AtomicReference<Foo.Constant> ref = new AtomicReference<Foo.Constant>(constant);
}
