package pkg2;

import pkg0.C;
import pkg1.E;

abstract class S extends C {
  int fail() {
    return e().m(); // ERROR: can't access m() because S is not a subtype of E.
  }

  abstract E e();
} 
