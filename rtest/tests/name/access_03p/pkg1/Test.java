package pkg1;

import pkg0.*;

class Test extends Base {
  int bar(Test arg) {
    return arg.foo.length;
  }
}
