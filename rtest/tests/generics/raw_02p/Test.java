// Test calling a method with a raw parameter using a parameterized type as argument.
// .result=COMPILE_PASS
public class Test extends ClassDecl {
  void test(Opt<Access> opt) {
    setChild(opt, 0);
  }

  public static void f(Test t, int i) {
  }
}

abstract class ASTNode<T extends ASTNode> {
  public void setChild(ASTNode node, int i) {
  }
}

class Opt<T extends ASTNode> extends ASTNode<T> { }

class Access extends ASTNode { }

class ClassDecl extends ASTNode<ASTNode> { }
