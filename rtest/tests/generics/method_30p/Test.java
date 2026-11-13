// Test proper handling of type erasure for override compatibility.
// See issue 66.
// .result: COMPILE_PASS
interface Släkte<T> { }
interface Forest { void milkweed(Släkte<String> RVw8XQtRGU8); }
interface Green  { void milkweed(Släkte RVw8XQtRGU8); }
interface Test extends Forest, Green { }

interface Flower {      void monarch_butterfly(Släkte GyjEU8eptQg); }
interface Host   { <uh> void monarch_butterfly(Släkte<Integer> GyjEU8eptQg); }
interface Test2 extends Flower, Host { }
