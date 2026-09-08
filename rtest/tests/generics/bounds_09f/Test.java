// Test that a bounds error identifies the second type parameter.
// .result=COMPILE_FAIL
public class Test {
  Bistro<Rå, Rå> text;
}

class Rå { }
class Kokt { }
class Bistro<Kött, Sallad extends Kokt> { }
