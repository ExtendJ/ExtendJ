// Test conditional expression type analysis.
// The type of a conditional expression with two result
// expressions of reference type is the lowest upper bound of the two types.
// https://bitbucket.org/extendj/extendj/issues/188/intersection-type-capture-conversion-error
// .result=COMPILE_PASS

// Over-designed OOP inheritance garbage.
interface Location { }
interface Locatable { }
interface Named { }
interface Ownable { }
abstract class GObject { }
abstract class GameObject extends GObject { }
abstract class UnitLocation extends GameObject implements Location {  }
abstract class GoodsLocation extends UnitLocation { }
class Unit extends GoodsLocation implements Locatable, Ownable { }
class Resource extends GoodsLocation implements Named, Ownable { }

class Test {
  void foo(Resource resource, Unit unit) {
    // Location is a commmon ancestor type of Resource and Unit.
    Location l = (resource != null) ? resource : unit;
  }
}
