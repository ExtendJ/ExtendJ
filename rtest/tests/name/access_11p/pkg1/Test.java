package pkg1;

import pkg0.*;

class Test extends Base {
  void bar(Test arg) {
    arg.foo();
  }
}
