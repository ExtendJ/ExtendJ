// Test incorrect conditional expression assignment.
// .result=COMPILE_FAIL

interface Ownable { }
interface Thing { }
abstract class Entity implements Thing { }
abstract class NotEntity { }
class Unit extends Entity implements Ownable { }
class Resource extends NotEntity implements Ownable { }

class Test {
  void foo(Resource resource, Unit unit) {
    // Thing is not a common supertype of Resource and Unit.
    Thing l = (resource != null) ? resource : unit;
  }
}
