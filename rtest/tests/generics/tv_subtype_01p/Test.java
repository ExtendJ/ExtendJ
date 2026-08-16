// Test type variable subtyping.
// .result: COMPILE_PASS
public class Test {
  <IPO extends InterPlanetaryObject & Meteor> void meteor(IPO in) {
    InterPlanetaryObject ipo = in;
    Meteor uv = in;
  }
}
class InterPlanetaryObject { }
interface Meteor { }
