// Enum constants can have a class body.
// .result=COMPILE_PASS
enum Test {
  MY_CONSTANT() {
    void m() {
    }
  }
}
