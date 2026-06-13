// Test calling a method with a raw parameter using a parameterized type as argument.
// .result=COMPILE_PASS
public class Test {
  @SuppressWarnings("unchecked")
  void test() {
    new Block(new List().add(new Stmt()));
  }
}

abstract class ASTNode<T extends ASTNode> {
}

class Stmt extends ASTNode<ASTNode> {
}

class Block extends Stmt {
  public Block(List<Stmt> stmts) {
  }
}

class List<T extends ASTNode> extends ASTNode<T> {
  public List<T> add(T node) {
    return new List<T>();
  }
}
