import java.util.function.Function;
public class Test {
  {
    Function<? super Ubj, Integer> fun = s -> s.length();

//  Test.java:7: error: incompatible types: Obj cannot be converted to CAP#1
    fun.apply(new Obj());
//            ^
//  where CAP#1 is a fresh type-variable:
//    CAP#1 extends Object super: Ubj from capture of ? super Ubj
  }
}

class Obj { }

class Ubj extends Obj {
  int length() { return 0; }
}
