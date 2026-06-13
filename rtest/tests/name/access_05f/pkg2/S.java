package pkg2;

import pkg0.C;
import pkg1.E;

class S extends C {
  int fail(E e) {
    return e.instance_var; // ERROR: can't access instance_var because S is not a subtype of E.
  }
} 
