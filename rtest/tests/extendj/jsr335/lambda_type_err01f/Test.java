// Various lambda type inference uses.
// .result=COMPILE_ERR_OUTPUT
public class Test {
  Fun1<String, String>      fun1a = s -> "a";
  Fun1<? extends String, ?> fun1b = s -> "b"; // Error.
  Fun1<String, ?>           fun1c = s -> "c"; // Error.
  Fun2<String, ?>           fun2 = s -> "c";  // Error.
}

interface Fun1<I extends R, R> {
  R fun(I i);
}

interface Fun2<I, R extends I> {
  R fun(I i);
}
