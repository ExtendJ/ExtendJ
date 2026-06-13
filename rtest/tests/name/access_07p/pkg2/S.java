package pkg2;

import pkg0.C;
import pkg1.E;

class S extends C {
  int pass() {
    return e().static_field; // OK:  static_field is declared in C, S is sub-type of C.
  }

  E e() {
    return null;
  }
} 
