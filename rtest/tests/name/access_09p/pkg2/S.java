package pkg2;

import pkg0.C;
import pkg1.E;

abstract class S extends C {
  int pass() {
    return e().staticMethod();
  }

  abstract E e();
} 
